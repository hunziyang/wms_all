package com.yang.cloud.wms_all.common.vo.product;

import com.yang.cloud.wms_all.common.utils.Pagination;
import lombok.Data;

@Data
public class ProductEsSelectVo {

    private String content;
    private boolean orderByCreatedTimeASC;
    private Pagination pagination = new Pagination();
}
