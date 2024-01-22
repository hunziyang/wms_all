package com.yang.cloud.wms_all.trade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 订单(Trade)实体类
 *
 * @author makejava
 * @since 2024-01-25 10:32:52
 */
@Data
@Accessors(chain = true)
@TableName("TRADE")
@Document(indexName = "wms_trade")
public class Trade extends EsEntity {
    /**
     * 订单
     */
    @Field(name = "NUMBER",type = FieldType.Keyword)
    private String number;
    /**
     * 状态
     */
    @Field(name = "STATUS",type = FieldType.Boolean)
    private Boolean status;
    /**
     * 原因
     */
    @Field(name = "MESSAGE",type = FieldType.Text)
    private String message;
}

