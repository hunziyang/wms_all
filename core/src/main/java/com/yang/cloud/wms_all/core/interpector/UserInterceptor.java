package com.yang.cloud.wms_all.core.interpector;

import com.yang.cloud.wms_all.core.contextHolder.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String JWT = "JWT";
    private static final String REQUEST_ID = "REQUEST_ID";
    private static final Long DEFAULT_USER_ID = -1L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader(REQUEST_ID);
        String username = StringUtils.isNotBlank(request.getHeader(USER_NAME)) ? request.getHeader(USER_NAME) : requestId;
        Long userId = StringUtils.isNotBlank(request.getHeader(USER_ID)) ? Long.valueOf(request.getHeader(USER_ID)) : DEFAULT_USER_ID;
        String jwt = request.getHeader(JWT);
        UserContextHolder.setUsername(username);
        UserContextHolder.setUserId(userId);
        UserContextHolder.setJwt(jwt);
        UserContextHolder.setRequestId(requestId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.close();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
