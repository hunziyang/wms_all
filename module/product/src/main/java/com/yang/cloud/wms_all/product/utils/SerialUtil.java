package com.yang.cloud.wms_all.product.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class SerialUtil {

    private static final StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String SERIAL_PREFIX = "S";

    public static String getSerial(ZonedDateTime zonedDateTime) {
        String date = zonedDateTime.format(dateTimeFormatter);
        String suffix = getSerialSuffix(String.format("%s%s", SERIAL_PREFIX, date));
        return String.format("%s%s%s", SERIAL_PREFIX, date, suffix);
    }

    private static String getSerialSuffix(String key) {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/numberSuffix.lua")));
        String tradeSuffix = stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(key));
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = tradeSuffix.length(); i < 8; i++) {
            stringBuffer.append("0");
        }
        return stringBuffer.append(tradeSuffix).toString();
    }
}
