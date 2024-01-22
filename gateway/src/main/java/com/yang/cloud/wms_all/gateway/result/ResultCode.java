package com.yang.cloud.wms_all.gateway.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode implements ResultCodeBase {
    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    FEIGN_ERROR(600, "Feign 调用异常"),
    GATEWAY_ERROR(701, "gateway err");


    private Integer code;
    private String msg;
}
