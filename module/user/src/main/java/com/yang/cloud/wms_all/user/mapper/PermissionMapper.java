package com.yang.cloud.wms_all.user.mapper;

import com.yang.cloud.wms_all.user.entity.Permission;
import com.yang.cloud.wms_all.core.mybatis.WmsMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限(Permission)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-23 18:22:54
 */
public interface PermissionMapper extends WmsMapper<Permission> {
    List<Permission> getPermissionsByUserId(@Param("userId") Long id);
}

