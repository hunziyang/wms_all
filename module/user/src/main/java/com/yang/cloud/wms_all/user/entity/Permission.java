package com.yang.cloud.wms_all.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 权限(Permission)实体类
 *
 * @author makejava
 * @since 2024-01-23 18:06:08
 */
@TableName("PERMISSION")
@Data
@Accessors(chain = true)
public class Permission extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String url;
}

