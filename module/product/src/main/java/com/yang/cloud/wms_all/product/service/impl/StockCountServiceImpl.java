package com.yang.cloud.wms_all.product.service.impl;

import com.yang.cloud.wms_all.common.vo.trade.ProductDetail;
import com.yang.cloud.wms_all.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms_all.core.exception.BaseException;
import com.yang.cloud.wms_all.core.result.ResultCode;
import com.yang.cloud.wms_all.core.util.MapUtil;
import com.yang.cloud.wms_all.product.entity.StockDetail;
import com.yang.cloud.wms_all.product.mapper.StockDetailMapper;
import com.yang.cloud.wms_all.product.mapper.StockMapper;
import com.yang.cloud.wms_all.product.service.StockCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockCountServiceImpl implements StockCountService {


    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockDetailMapper stockDetailMapper;

    @Override
    @Transactional
    public void stocking(ProductDetail productDetail, String message, String number) {
        int count;
        if ("incrby".equals(message)) {
            count = stockMapper.incrStock(productDetail);
        } else {
            count = stockMapper.createStock(productDetail);
        }
        if (count == 0) {
            throw new BaseException(ResultCode.SQL_ERROR,
                    MapUtil.getErrorMap(String.format("商品 %s 更新库存 %d 失败", productDetail.getProductCode(), productDetail.getCount())));
        }
        StockDetail stockDetail = new StockDetail();
        stockDetail.setCount(productDetail.getCount())
                .setFlag(true)
                .setNumber(number)
                .setProductId(productDetail.getProductId())
                .setCreatedId(UserContextHolder.getUserId())
                .setCreatedName(UserContextHolder.getUsername())
                .setUpdatedId(UserContextHolder.getUserId())
                .setUpdatedName(UserContextHolder.getUsername());
        stockDetailMapper.insert(stockDetail);
        if (count == 0) {
            throw new BaseException(ResultCode.SQL_ERROR,
                    MapUtil.getErrorMap(String.format("商品 %s 更新库存 %d 记录失败", productDetail.getProductCode(), productDetail.getCount())));
        }
    }
}
