package com.yang.cloud.wms_all.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.cloud.wms_all.gateway.exception.BaseException;
import com.yang.cloud.wms_all.gateway.jackson.JacksonConfig;
import com.yang.cloud.wms_all.gateway.result.Result;
import com.yang.cloud.wms_all.gateway.result.ResultCode;
import com.yang.cloud.wms_all.gateway.utils.MapUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

@Slf4j
public class FeignErrorDecode implements ErrorDecoder {

    private static final ObjectMapper objectMapper = new JacksonConfig().objectMapper();

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        try {
            Result result = objectMapper.readValue(response.body().asInputStream(), Result.class);
            return new BaseException(ResultCode.FEIGN_ERROR,
                    ObjectUtils.isNotEmpty(result.getErrors()) ? result.getErrors() : MapUtil.getErrorMap(result.getMsg()));
        } catch (IOException e) {
            Exception exception = new Default().decode(s, response);
            log.warn("feign decode err", exception);
            return new BaseException(ResultCode.FEIGN_ERROR, MapUtil.getErrorMap(exception.getMessage()));
        }
    }
}
