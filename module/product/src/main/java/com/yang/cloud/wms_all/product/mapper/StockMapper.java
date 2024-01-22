package com.yang.cloud.wms_all.product.mapper;

import com.yang.cloud.wms_all.common.vo.trade.ProductDetail;
import com.yang.cloud.wms_all.core.mybatis.WmsMapper;
import com.yang.cloud.wms_all.product.entity.Stock;

public interface StockMapper extends WmsMapper<Stock> {
    int incrStock(ProductDetail productDetail);

    int createStock(ProductDetail productDetail);

    int decrStock(ProductDetail productDetail);
}
