package com.yang.cloud.wms_all.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Configuration
public class EsConfig extends ElasticsearchConfigurationSupport {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final String SERVER_ZONE = "Asia/Shanghai";


    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(StringToZoneDateTimeConvert.INSTANCE, ZonedDateTimeToStringConvert.INSTANCE));
    }

    @ReadingConverter
    enum StringToZoneDateTimeConvert implements Converter<String, ZonedDateTime> {
        INSTANCE;

        @Override
        public ZonedDateTime convert(String source) {
            return LocalDateTime.parse(source, dateTimeFormatter).atZone(ZoneId.of(SERVER_ZONE));
        }
    }

    @WritingConverter
    enum ZonedDateTimeToStringConvert implements Converter<ZonedDateTime, String> {
        INSTANCE;

        @Override
        public String convert(ZonedDateTime source) {
            return dateTimeFormatter.format(source);
        }
    }

}
