package com.yang.cloud.wms_all.product.service;

import com.yang.cloud.wms_all.common.vo.product.ProductVo;
import com.yang.cloud.wms_all.common.vo.product.ProductEsSelectVo;
import com.yang.cloud.wms_all.common.vo.product.ProductSelectVo;
import com.yang.cloud.wms_all.core.util.PagedList;
import com.yang.cloud.wms_all.product.entity.Product;

public interface ProductService {
    void create(ProductVo productVo);

    PagedList<Product> selectBySQL(ProductSelectVo productSelectVo);

    PagedList<Product> selectByES(ProductEsSelectVo productEsSelectVo);

    void updateProduct(Long id, ProductVo productVo);

    void deleteProduct(Long id);
}
