package com.yang.cloud.wms_all.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单详情(TradeDetail)实体类
 *
 * @author makejava
 * @since 2024-01-26 13:51:36
 */
@Accessors(chain = true)
@Data
@TableName("TRADE_DETAIL")
public class TradeDetail extends BaseEntity {
/**
     * 订单编号
     */
    private Long tradeId;
/**
     * 商品编号
     */
    private Long productId;
/**
     * 数量
     */
    private Integer count;
}

