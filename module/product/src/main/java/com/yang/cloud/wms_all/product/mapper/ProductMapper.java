package com.yang.cloud.wms_all.product.mapper;

import com.yang.cloud.wms_all.common.vo.product.ProductSelectVo;
import com.yang.cloud.wms_all.common.vo.product.ProductVo;
import com.yang.cloud.wms_all.core.mybatis.WmsMapper;
import com.yang.cloud.wms_all.product.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * 商品(Product)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-24 10:49:35
 */
public interface ProductMapper extends WmsMapper<Product> {
    List<Product> selectByCondition(@Param("query") ProductSelectVo productSelectVo, @Param("offset") Integer offset, @Param("count") Integer pageSize);

    Long selectCountByCondition(@Param("query") ProductSelectVo productSelectVo);

    void updateProductById(@Param("id") Long id, @Param("product") ProductVo productVo, @Param("userId") Long userId, @Param("username") String username, @Param("time") ZonedDateTime now);

    void deleteProductById(Product product);

    Product selectProductById(Long id);
}

