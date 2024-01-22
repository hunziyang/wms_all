package com.yang.cloud.wms_all.user.service;

import com.yang.cloud.wms_all.common.dto.user.UserLoginDto;
import com.yang.cloud.wms_all.common.vo.user.UserJwtDetailVo;
import com.yang.cloud.wms_all.common.vo.user.UserLoginVo;
import com.yang.cloud.wms_all.common.vo.user.UserRegisterVo;

public interface UserService {
    void register(UserRegisterVo userRegisterVo);

    UserLoginDto login(UserLoginVo userLoginVo);

    UserLoginDto jwtDetail(UserJwtDetailVo userJwtDetailVo);
}
