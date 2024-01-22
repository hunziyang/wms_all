package com.yang.cloud.wms_all.common.vo.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductVo {

    @NotBlank(message = "name 不能为空")
    private String name;

    @NotBlank(message = "code 不能为空")
    private String code;

    @NotBlank(message = "sku 不能为空")
    private String sku;

    @NotBlank(message = "properties 不能为空")
    private String properties;
}
