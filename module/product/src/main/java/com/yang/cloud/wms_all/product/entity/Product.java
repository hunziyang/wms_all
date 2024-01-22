package com.yang.cloud.wms_all.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 商品(Product)实体类
 *
 * @author makejava
 * @since 2024-01-24 10:36:37
 */
@TableName("PRODUCT")
@Data
@Accessors(chain = true)
@Document(indexName = "wms_product")
public class Product extends EsEntity {
    /**
     * 商品名称
     */
    @Field(name = "NAME", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;
    /**
     * 商品编码
     */
    @Field(name = "CODE", type = FieldType.Keyword)
    private String code;
    /**
     * 型号
     */
    @Field(name = "SKU", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String sku;
    /**
     * 属性
     */
    @Field(name = "PROPERTIES", type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String properties;

}

