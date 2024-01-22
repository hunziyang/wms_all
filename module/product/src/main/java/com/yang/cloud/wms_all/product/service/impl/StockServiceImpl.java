package com.yang.cloud.wms_all.product.service.impl;

import com.yang.cloud.wms_all.common.vo.product.StockVo;
import com.yang.cloud.wms_all.common.vo.trade.ProductDetail;
import com.yang.cloud.wms_all.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms_all.core.exception.InternalServerErrorException;
import com.yang.cloud.wms_all.core.util.MapUtil;
import com.yang.cloud.wms_all.product.entity.StockDetail;
import com.yang.cloud.wms_all.product.mapper.StockDetailMapper;
import com.yang.cloud.wms_all.product.mapper.StockMapper;
import com.yang.cloud.wms_all.product.service.StockCountService;
import com.yang.cloud.wms_all.product.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockCountService stockCountService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockDetailMapper stockDetailMapper;


    private static final String STOCK_REDIS_PREFIX = "STOCK_";

    @Override
    public Map<String, String> stocking(StockVo stockVo) {
        Map<String, String> errMap = new HashMap<>();
        stockVo.getProductDetails().forEach(productDetail -> {
            try {
                String message = updateOneRedisCount(productDetail, "/lua/stocking.lua", true);
                if ("err".equals(message)) {
                    errMap.put(productDetail.getProductCode(), "redis incr err");
                    return;
                }
                incrSQLCount(productDetail, message, stockVo, errMap);
            } catch (Exception e) {
                log.warn("stocking err,productCode:{},productId:{},count:{}",
                        productDetail.getProductCode(),
                        productDetail.getProductId(),
                        productDetail.getCount());
                errMap.put(productDetail.getProductCode(), e.getMessage());
            }
        });
        return errMap;
    }

    private void incrSQLCount(ProductDetail productDetail, String message, StockVo stockVo, Map<String, String> errMap) {
        try {
            stockCountService.stocking(productDetail, message, stockVo.getNumber());
        } catch (Exception e) {
            log.warn("update mysql stock or detail err,productCode:{},productId:{},count:{}",
                    productDetail.getProductCode(),
                    productDetail.getProductId(),
                    productDetail.getCount());
            errMap.put(productDetail.getProductCode(), "operation sql err");
            updateOneRedisCount(productDetail, "/lua/rollback.lua", false);
        }
    }

    private String updateOneRedisCount(ProductDetail productDetail, String path, boolean flag) {
        try {
            DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
            defaultRedisScript.setResultType(String.class);
            defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
            return stringRedisTemplate.execute(defaultRedisScript,
                    Arrays.asList(String.format("%s%d", STOCK_REDIS_PREFIX, productDetail.getProductId())),
                    new String[]{String.valueOf(productDetail.getCount())});
        } catch (Exception e) {
            log.warn("redis update err, prodcutId:{}, count:{},incr :{}",
                    productDetail.getProductId(), productDetail.getCount(), flag, e);
            return "err";
        }
    }

    @Override
    @Transactional
    public void outbound(StockVo stockVo) {
        List<String> productIds = new ArrayList<>();
        String[] counts = new String[stockVo.getProductDetails().size()];
        for (int i = 0; i < stockVo.getProductDetails().size(); i++) {
            ProductDetail detail = stockVo.getProductDetails().get(i);
            productIds.add(String.format("%s%d", STOCK_REDIS_PREFIX, detail.getProductId()));
            counts[i] = String.valueOf(detail.getCount());
        }
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/outbound.lua")));
        String flag = stringRedisTemplate.execute(defaultRedisScript, productIds, counts);
        if (!"success".equals(flag)) {
            throw new InternalServerErrorException(MapUtil.getErrorMap(String.format("%s:库存不足", flag)));
        }
        stockVo.getProductDetails().forEach(productDetail -> {
            stockMapper.decrStock(productDetail);
            StockDetail stockDetail = new StockDetail();
            stockDetail.setCount(productDetail.getCount())
                    .setFlag(false)
                    .setNumber(stockVo.getNumber())
                    .setProductId(productDetail.getProductId())
                    .setCreatedId(UserContextHolder.getUserId())
                    .setCreatedName(UserContextHolder.getUsername())
                    .setUpdatedId(UserContextHolder.getUserId())
                    .setUpdatedName(UserContextHolder.getUsername());
            stockDetailMapper.insert(stockDetail);
        });
    }
}
