package com.yang.cloud.wms_all.core.annontation;

import com.yang.cloud.wms_all.Wms;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

import java.lang.annotation.*;

@SpringBootApplication(scanBasePackageClasses = Wms.class,nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WmsApplication {
}
