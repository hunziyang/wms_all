package com.yang.cloud.wms_all.common.vo.trade;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDetail {

    @NotBlank(message = "productCode 不能为空")
    private String productCode;

    @NotNull(message = "productId 不能为空")
    private Long productId;

    @NotNull(message = "num 不能为空")
    private Integer count;
}
