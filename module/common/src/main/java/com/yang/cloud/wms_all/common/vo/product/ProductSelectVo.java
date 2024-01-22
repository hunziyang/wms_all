package com.yang.cloud.wms_all.common.vo.product;

import com.yang.cloud.wms_all.common.utils.Pagination;
import lombok.Data;

@Data
public class ProductSelectVo {

    private String name;
    private String code;
    private String properties;
    private String sku;
    private boolean orderByCreatedTimeASC;
    private Pagination pagination = new Pagination();
}
