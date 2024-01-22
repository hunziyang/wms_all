package com.yang.cloud.wms_all.trade.controller;

import com.yang.cloud.wms_all.common.vo.trade.TradeCreateVo;
import com.yang.cloud.wms_all.common.vo.trade.TradeSelectVo;
import com.yang.cloud.wms_all.core.annontation.WmsController;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WmsController("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/create")
    public Result create(@Validated @RequestBody TradeCreateVo tradeCreateVo){
        return tradeService.create(tradeCreateVo);
    }

    @GetMapping("/select")
    public Result select(@Validated TradeSelectVo tradeSelectVo){
        return Result.success(tradeService.select(tradeSelectVo));
    }
}
