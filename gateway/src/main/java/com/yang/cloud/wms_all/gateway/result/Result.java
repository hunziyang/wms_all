package com.yang.cloud.wms_all.gateway.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Result<T> {

    private Integer code;
    private String msg;
    private T body;
    private Map<String, ?> errors;

    public static Result error(ResultCodeBase resultCodeBase) {
        return new Result().setCode(resultCodeBase.getCode()).setMsg(resultCodeBase.getMsg());
    }

    public static Result error(ResultCodeBase resultCodeBase, Map<String, ?> errors) {
        return new Result().setCode(resultCodeBase.getCode()).setMsg(resultCodeBase.getMsg()).setErrors(errors);
    }
}
