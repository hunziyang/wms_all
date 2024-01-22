package com.yang.cloud.wms_all.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yang.cloud.wms_all.user.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(@Param("username") String username);
}
