package com.yang.cloud.wms_all.product.service;

import com.yang.cloud.wms_all.common.vo.trade.ProductDetail;

public interface StockCountService {
    void stocking(ProductDetail productDetail, String message, String trade);
}
