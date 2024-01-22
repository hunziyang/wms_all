package com.yang.cloud.wms_all.user.result;

import com.yang.cloud.wms_all.core.result.ResultCodeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultUserCode implements ResultCodeBase {

    USER_NOT_EXISTS(800, "User not exists"),
    USER_LOCKED(801, "User locked"),
    WRONG_PASSWORD(802, "WRONG PASSWORD");


    private Integer code;
    private String msg;
}
