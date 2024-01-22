package com.yang.cloud.wms_all.core.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.cloud.wms_all.core.exception.BaseException;
import com.yang.cloud.wms_all.core.jackson.JacksonConfig;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.core.result.ResultCode;
import com.yang.cloud.wms_all.core.util.MapUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class FeignErrorDecode implements ErrorDecoder {

    private static final ObjectMapper objectMapper = new JacksonConfig().objectMapper();

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        try {
            Result result = objectMapper.readValue(response.body().asInputStream(), Result.class);
            Map<String, ?> map = ObjectUtils.isNotEmpty(result.getErrors()) ? result.getErrors() : MapUtil.getErrorMap(result.getMsg());
            log.warn("err message:{}", map);
            return new BaseException(ResultCode.FEIGN_ERROR, map);
        } catch (IOException e) {
            Exception exception = new Default().decode(s, response);
            log.warn("feign decode err", exception);
            return new BaseException(ResultCode.FEIGN_ERROR, MapUtil.getErrorMap(exception.getMessage()));
        }
    }
}
