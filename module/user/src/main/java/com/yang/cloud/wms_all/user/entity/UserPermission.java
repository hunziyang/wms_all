package com.yang.cloud.wms_all.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms_all.core.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户权限(UserPermission)实体类
 *
 * @author makejava
 * @since 2024-01-23 18:06:16
 */
@Data
@TableName("USER_PERMISSION")
@Accessors(chain = true)
public class UserPermission extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 权限ID
     */
    private Long permissionId;
}

