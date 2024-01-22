package com.yang.cloud.wms_all.trade.feign;

import com.yang.cloud.wms_all.common.vo.product.StockVo;
import com.yang.cloud.wms_all.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "product-service", path = "/product-service")
public interface ProductFeignService {

    @PostMapping("/stock/stocking")
    Result<Map<String, String>> stocking(@Validated @RequestBody StockVo stockVo);

    @PostMapping("/stock/outbound")
    Result outbound(@Validated @RequestBody StockVo stockVo);
}
