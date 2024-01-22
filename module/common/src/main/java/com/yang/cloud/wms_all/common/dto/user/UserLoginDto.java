package com.yang.cloud.wms_all.common.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserLoginDto {

    private String jwt;
    private String userName;
    private Long userId;
    private List<String> permissions;
}
