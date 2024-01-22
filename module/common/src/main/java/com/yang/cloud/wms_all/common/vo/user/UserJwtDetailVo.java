package com.yang.cloud.wms_all.common.vo.user;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class UserJwtDetailVo {

    @NotBlank(message = "jwt 不能为空")
    private String jwt;
}
