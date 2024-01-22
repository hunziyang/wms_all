package com.yang.cloud.wms_all.gateway.exception;

import com.yang.cloud.wms_all.gateway.result.ResultCodeBase;
import lombok.Data;

import java.util.Map;

@Data
public class BaseException extends RuntimeException {

    private ResultCodeBase resultCodeBase;
    private Map<String, ?> errors;

    public BaseException(ResultCodeBase resultCodeBase) {
        super(resultCodeBase.getMsg());
        this.resultCodeBase = resultCodeBase;
    }


    public BaseException(ResultCodeBase resultCodeBase, Map<String, ?> errors) {
        super(resultCodeBase.getMsg());
        this.resultCodeBase = resultCodeBase;
        this.errors = errors;
    }
}
