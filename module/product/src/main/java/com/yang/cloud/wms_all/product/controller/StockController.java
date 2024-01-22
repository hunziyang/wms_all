package com.yang.cloud.wms_all.product.controller;

import com.yang.cloud.wms_all.common.vo.product.StockVo;
import com.yang.cloud.wms_all.core.annontation.WmsController;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WmsController("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/stocking")
    public Result stocking(@Validated @RequestBody StockVo stockVo){
        return Result.success(stockService.stocking(stockVo));
    }

    @PostMapping("/outbound")
    public Result outbound(@Validated @RequestBody StockVo stockVo){
        stockService.outbound(stockVo);
        return Result.success();
    }
}
