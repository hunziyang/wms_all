package com.yang.cloud.wms_all.core.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.function.Supplier;

@Configuration
@EnableTransactionManagement
@MapperScan("com.yang.cloud.wms_all.**.mapper")
public class MybatisPlusConfig implements MetaObjectHandler {

    private static final String SERVER_ZONE = "Asia/Shanghai";;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", ZonedDateTime.class, ZonedDateTime.now(ZoneId.of(SERVER_ZONE)));
        this.strictUpdateFill(metaObject, "updatedTime", ZonedDateTime.class, ZonedDateTime.now(ZoneId.of(SERVER_ZONE)));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedTime", ZonedDateTime.class, ZonedDateTime.now(ZoneId.of(SERVER_ZONE)));
    }

    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        Object obj = fieldVal.get();
        if (Objects.nonNull(obj)) {
            metaObject.setValue(fieldName, obj);
        }
        return this;
    }
}
