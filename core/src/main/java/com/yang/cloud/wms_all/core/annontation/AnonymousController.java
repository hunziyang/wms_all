package com.yang.cloud.wms_all.core.annontation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RestController
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping
public @interface AnonymousController {

    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] value() default {};

}
