package com.yang.cloud.wms_all.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色(Role)实体类
 *
 * @author makejava
 * @since 2024-01-23 18:06:08
 */
@Data
@TableName("ROLE")
@Accessors(chain = true)
public class Role extends BaseEntity {
    /**
     * 名称
     */
    private String name;
}

