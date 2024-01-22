package com.yang.cloud.wms_all.core.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagedList<T> {

    private List<T> result;
    private Integer pageNum;
    private Integer pageSize;
    private Long count;
}
