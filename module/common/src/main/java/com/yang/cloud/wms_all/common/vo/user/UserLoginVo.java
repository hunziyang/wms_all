package com.yang.cloud.wms_all.common.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginVo {

    @NotBlank(message = "username 不能为空")
    private String username;

    @NotBlank(message = "password 不能为空")
    private String password;
}
