package com.yang.cloud.wms_all.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode implements ResultCodeBase {

    SUCCESS(200, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    VALID_ERROR(505, "Method Argument Not Valid"),
    FEIGN_ERROR(600, "Feign 调用异常"),
    FLOW_ERROR(601, "流量限流"),
    DEGRADE_ERROR(602, "服务降级"),
    SENTINEL_ERROR(603, "sentinel 降级"),
    SQL_ERROR(1000, "数据更新失败");


    private Integer code;
    private String msg;
}
