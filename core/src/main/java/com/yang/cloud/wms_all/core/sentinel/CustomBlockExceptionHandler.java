package com.yang.cloud.wms_all.core.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.cloud.wms_all.core.jackson.JacksonConfig;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class CustomBlockExceptionHandler implements BlockExceptionHandler {

    private static final ObjectMapper objectMapper = new JacksonConfig().objectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.warn("blockException:{}", e.getRule(), e);
        Result result;
        if (e instanceof FlowException) {
            result = Result.error(ResultCode.FLOW_ERROR);
        } else if (e instanceof DegradeException) {
            result = Result.error(ResultCode.DEGRADE_ERROR);
        } else {
            result = Result.error(ResultCode.SENTINEL_ERROR);
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(500);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
