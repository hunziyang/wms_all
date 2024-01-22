package com.yang.cloud.wms_all.trade.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class EsEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @Id
    private Long id;
    /**
     * 唯一性锁
     */
    @Field(name = "UNIQUE_KEY", type = FieldType.Boolean)
    private Integer uniqueKey;
    /**
     * 乐观锁
     */
    @Version
    @Field(name = "REVISION", type = FieldType.Boolean)
    private Integer revision;
    /**
     * 逻辑删
     */
    @TableLogic
    @Field(name = "IS_DELETE", type = FieldType.Boolean)
    private Boolean isDelete;
    /**
     * 创建人编号
     */
    @Field(name = "CREATED_ID", type = FieldType.Long)
    private Long createdId;
    /**
     * 创建人姓名
     */
    @Field(name = "CREATED_NAME", type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String createdName;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(name = "CREATED_TIME",type = FieldType.Date)
    private ZonedDateTime createdTime;
    /**
     * 更新人编号
     */
    @Field(name = "UPDATED_ID",type = FieldType.Long)
    private Long updatedId;
    /**
     * 更新人姓名
     */
    @Field(name = "UPDATED_NAME",type = FieldType.Text,searchAnalyzer = "ik_max_word",analyzer = "ik_max_word")
    private String updatedName;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(name = "UPDATED_TIME",type = FieldType.Date)
    private ZonedDateTime updatedTime;
}
