package com.yang.cloud.wms_all.core.schedule;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ScheduleAspect {


    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public void aroundScheduled(ProceedingJoinPoint proceedingJoinPoint) {
        Signature signature = proceedingJoinPoint.getSignature();
        try {
            log.warn("{} start", signature.getDeclaringTypeName());
            Object proceed = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            log.warn("{} finish", signature.getDeclaringTypeName());
        } catch (Throwable e) {
            log.warn("{} err", signature.getDeclaringTypeName(), e);
        }
    }
}
