package com.yang.cloud.wms_all.user.controller.anonymous;

import com.yang.cloud.wms_all.common.vo.user.UserJwtDetailVo;
import com.yang.cloud.wms_all.common.vo.user.UserLoginVo;
import com.yang.cloud.wms_all.common.vo.user.UserRegisterVo;
import com.yang.cloud.wms_all.core.annontation.AnonymousController;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AnonymousController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserRegisterVo userRegisterVo) {
        userService.register(userRegisterVo);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody UserLoginVo userLoginVo) {
        return Result.success(userService.login(userLoginVo));
    }

    @PostMapping("/jwtDetail")
    public Result jwtDetail(@Validated @RequestBody UserJwtDetailVo userJwtDetailVo) {
        return Result.success(userService.jwtDetail(userJwtDetailVo));
    }
}
