package com.yang.cloud.wms_all.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 库存详情(StockDetail)实体类
 *
 * @author makejava
 * @since 2024-01-25 17:51:06
 */
@Data
@Accessors(chain = true)
@TableName("STOCK_DETAIL")
public class StockDetail extends BaseEntity {
    /**
     * 订单号
     */
    private String number;
    /**
     * 出入库标志
     */
    private Boolean flag;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer count;
}

