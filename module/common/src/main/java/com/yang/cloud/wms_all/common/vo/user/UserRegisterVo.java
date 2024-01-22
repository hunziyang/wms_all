package com.yang.cloud.wms_all.common.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class UserRegisterVo {

    @NotBlank(message = "username 不能为空")
    private String username;

    @NotBlank(message = "password 不能为空")
    private String password;

    @NotBlank(message = "name 不能为空")
    private String name;

    @NotBlank(message = "phone 不能为空")
    private String phone;
    private Integer gender;
    private LocalDate birthday;
}
