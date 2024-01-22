package com.yang.cloud.wms_all.common.utils;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class Pagination {

    @Min(1)
    private Integer pageNum = 1;
    @Max(1000)
    private Integer pageSize = 20;
    private String orderBy;

    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }

}
