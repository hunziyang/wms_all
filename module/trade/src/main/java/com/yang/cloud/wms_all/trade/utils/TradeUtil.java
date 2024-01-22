package com.yang.cloud.wms_all.trade.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class TradeUtil {

    private static final StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String TRADE_PREFIX = "T";
    private static final String REFUND_TRADE_PREFIX = "R";

    public static String getTrade(ZonedDateTime zonedDateTime, Boolean isRefund) {
        String date = zonedDateTime.format(dateTimeFormatter);
        String suffix = getTradeSuffix(String.format("%s%s", isRefund ? REFUND_TRADE_PREFIX : TRADE_PREFIX, date));
        return String.format("%s%s%s", isRefund ? REFUND_TRADE_PREFIX : TRADE_PREFIX, date, suffix);
    }

    private static String getTradeSuffix(String key) {
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
