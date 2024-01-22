package com.yang.cloud.wms_all.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色权限(RolePermission)实体类
 *
 * @author makejava
 * @since 2024-01-23 18:06:08
 */
@Data
@TableName("ROLE_PERMISSION")
@Accessors(chain = true)
public class RolePermission extends BaseEntity {
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 权限ID
     */
    private String permissionId;
}

