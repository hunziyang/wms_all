package com.yang.cloud.wms_all.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @Id
    private Long id;
    /**
     * 唯一性锁
     */
    private Integer uniqueKey;
    /**
     * 乐观锁
     */
    @Version
    private Integer revision;
    /**
     * 逻辑删
     */
    @TableLogic
    private Boolean isDelete;
    /**
     * 创建人编号
     */
    private Long createdId;
    /**
     * 创建人姓名
     */
    private String createdName;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private ZonedDateTime createdTime;
    /**
     * 更新人编号
     */
    private Long updatedId;
    /**
     * 更新人姓名
     */
    private String updatedName;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private ZonedDateTime updatedTime;
}
