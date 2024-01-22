package com.yang.cloud.wms_all.user.controller;

import com.yang.cloud.wms_all.core.annontation.WmsController;
import com.yang.cloud.wms_all.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms_all.core.result.Result;
import org.springframework.web.bind.annotation.PostMapping;

@WmsController("/user")
public class UserController {

    @PostMapping("updatePassword")
    public Result updatePassword(){
        return Result.success(UserContextHolder.getUserId());
    }
}
