package com.yang.cloud.wms_all.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 库存(Stock)实体类
 *
 * @author makejava
 * @since 2024-01-25 15:15:41
 */
@Data
@TableName("STOCK")
@Accessors(chain = true)
public class Stock extends BaseEntity {
    /**
     * 商品ID
     */
    private String productId;
    /**
     * 数量
     */
    private Integer count;
}

