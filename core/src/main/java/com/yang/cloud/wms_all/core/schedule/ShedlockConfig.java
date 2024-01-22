package com.yang.cloud.wms_all.core.schedule;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.TimeZone;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT1H")
@SuppressWarnings("all")
public class ShedlockConfig {

    private static final String TABLE_NAME = "SHEDLOCK";
    private static final String NAME = "NAME";
    private static final String LOCK_UNTIL = "LOCK_UNTIL";
    private static final String LOCKED_AT = "LOCKED_AT";
    private static final String LOCKED_BY = "LOCKED_BY";
    private static final String SERVER_ZONE = "Asia/Shanghai";;


    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(JdbcTemplateLockProvider.Configuration.builder()
                .withJdbcTemplate(new JdbcTemplate(dataSource))
                .withTimeZone(TimeZone.getTimeZone(SERVER_ZONE))
                .withTableName(TABLE_NAME)
                .withColumnNames(new JdbcTemplateLockProvider.ColumnNames(
                        NAME,
                        LOCK_UNTIL,
                        LOCKED_AT,
                        LOCKED_BY))
                .build());
    }
}
