package com.yang.cloud.wms_all.product.service;


import com.yang.cloud.wms_all.common.vo.product.StockVo;

import java.util.Map;

public interface StockService {
    Map<String, String> stocking(StockVo stockVo);

    void outbound(StockVo stockVo);
}
