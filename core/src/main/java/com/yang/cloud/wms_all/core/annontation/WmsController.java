package com.yang.cloud.wms_all.core.annontation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@RequestMapping
public @interface WmsController {

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] value() default {};
}
