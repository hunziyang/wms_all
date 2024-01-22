package com.yang.cloud.wms_all.core.feign;

import com.yang.cloud.wms_all.core.contextHolder.UserContextHolder;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

@Configuration
public class FeignConfig implements RequestInterceptor {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String JWT = "JWT";
    private static final String REQUEST_ID = "REQUEST_ID";

    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options options(){
        return new Request.Options(5000,5000);
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeignErrorDecode();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(USER_NAME, UserContextHolder.getUsername());
        requestTemplate.header(USER_ID, String.valueOf(UserContextHolder.getUserId()));
        requestTemplate.header(JWT, UserContextHolder.getJwt());
        requestTemplate.header(REQUEST_ID, UserContextHolder.getRequestId());
    }
}
