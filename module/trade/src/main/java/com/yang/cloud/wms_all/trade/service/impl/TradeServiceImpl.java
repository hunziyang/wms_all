package com.yang.cloud.wms_all.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.cloud.wms_all.common.vo.product.StockVo;
import com.yang.cloud.wms_all.common.vo.trade.TradeCreateVo;
import com.yang.cloud.wms_all.common.vo.trade.TradeSelectVo;
import com.yang.cloud.wms_all.core.CoreConstant;
import com.yang.cloud.wms_all.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms_all.core.exception.BaseException;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.core.result.ResultCode;
import com.yang.cloud.wms_all.core.util.PagedList;
import com.yang.cloud.wms_all.trade.dto.TradeDetailDto;
import com.yang.cloud.wms_all.trade.entity.Trade;
import com.yang.cloud.wms_all.trade.entity.TradeDetail;
import com.yang.cloud.wms_all.trade.feign.ProductFeignService;
import com.yang.cloud.wms_all.trade.mapper.TradeDetailMapper;
import com.yang.cloud.wms_all.trade.mapper.TradeMapper;
import com.yang.cloud.wms_all.trade.service.TradeService;
import com.yang.cloud.wms_all.trade.utils.TradeUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private TradeDetailMapper tradeDetailMapper;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    @Transactional
    public Result create(TradeCreateVo tradeCreateVo) {
        String number = TradeUtil.getTrade(ZonedDateTime.now(ZoneId.of(CoreConstant.SERVER_ZONE)), tradeCreateVo.getTradeType());
        Long tradeId = createTrade(tradeCreateVo, number);
        StockVo stockVo = new StockVo()
                .setNumber(number)
                .setProductDetails(tradeCreateVo.getProductDetails());
        boolean flag;
        if (!tradeCreateVo.getTradeType()) {
            flag = incrStock(stockVo, tradeId);
        } else {
            flag = decrStock(stockVo, tradeId);
        }
        if (!flag){
            return Result.error(ResultCode.FEIGN_ERROR);
        }
        return Result.success();
    }

    private Long createTrade(TradeCreateVo tradeCreateVo, String number) {
        Trade trade = new Trade()
                .setNumber(number)
                .setStatus(true);
        trade.setCreatedId(UserContextHolder.getUserId())
                .setCreatedName(UserContextHolder.getUsername());
        tradeMapper.insert(trade);
        tradeCreateVo.getProductDetails().forEach(productDetail -> {
            TradeDetail tradeDetail = new TradeDetail();
            tradeDetail.setTradeId(trade.getId())
                    .setCount(productDetail.getCount())
                    .setProductId(productDetail.getProductId())
                    .setCreatedId(UserContextHolder.getUserId())
                    .setCreatedName(UserContextHolder.getUsername())
                    .setUpdatedId(UserContextHolder.getUserId())
                    .setUpdatedName(UserContextHolder.getUsername());
            tradeDetailMapper.insert(tradeDetail);
        });
        return trade.getId();
    }

    @SneakyThrows
    private boolean incrStock(StockVo stockVo, Long tradeId) {
        Result<Map<String, String>> stocking;
        try {
            stocking = productFeignService.stocking(stockVo);
        } catch (Exception e) {
            log.warn("Feign product stocking err", e);
            updateTradeStatus(tradeId, e.getMessage());
            return false;
        }
        if (stocking.getBody().size() > 0) {
            updateTradeStatus(tradeId, objectMapper.writeValueAsString(stocking.getBody()));
            return false;
        }
        return true;
    }

    private void updateTradeStatus(Long tradeId, String message) {
        LambdaUpdateWrapper<Trade> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Trade::getStatus, false)
                .set(Trade::getMessage, message)
                .eq(Trade::getId, tradeId)
                .eq(Trade::getIsDelete, false);
        tradeMapper.update(lambdaUpdateWrapper);
    }

    private boolean decrStock(StockVo stockVo, Long tradeId) {
        try {
            productFeignService.outbound(stockVo);
            return true;
        } catch (BaseException e) {
            log.warn("Feign product stocking err", e);
            updateTradeStatus(tradeId, e.getMessage());
            return false;
        }
    }

    @Override
    public PagedList<TradeDetailDto> select(TradeSelectVo tradeSelectVo) {
        List<TradeDetailDto> tradeDetailDtos = tradeMapper.select(tradeSelectVo,tradeSelectVo.getPagination().getOffset(),tradeSelectVo.getPagination().getPageSize());
        Long count = tradeMapper.selectCount(tradeSelectVo);
        return PagedList.<TradeDetailDto>builder()
                .count(count)
                .result(tradeDetailDtos)
                .pageNum(tradeSelectVo.getPagination().getPageNum())
                .pageSize(tradeSelectVo.getPagination().getPageSize())
                .build();
    }
}
