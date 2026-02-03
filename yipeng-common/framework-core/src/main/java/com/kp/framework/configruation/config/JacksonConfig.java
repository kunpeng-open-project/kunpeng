package com.kp.framework.configruation.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kp.framework.utils.kptool.KPDateUtil;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 全局类型转换器。
 * @author lipeng
 * 2020/9/23
 */
@Configuration
@Component
public class JacksonConfig {

    /**
     * 全局转化器。
     * @author lipeng
     * 2020/9/23
     * @return org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Jackson2ObjectMapperBuilderCustomizer cunstomizer = new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                //deserializerByType  入参  serializerByType 出参
                jacksonObjectMapperBuilder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(KPDateUtil.DATE_PATTERN)));
                jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(KPDateUtil.DATE_TIME_PATTERN)));
                jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(KPDateUtil.DATE_TIME_PATTERN)));
                jacksonObjectMapperBuilder.serializerByType(String.class, new MyStringSerializer());
                //下面俩句是吧Long 转 string
//                jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
//                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
//                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            }
        };
        return cunstomizer;
    }

    class MyStringSerializer extends StdSerializer<String> {
        public MyStringSerializer() {
            super(String.class);
        }

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null || value.isEmpty()) {
                gen.writeNull();
            } else {
                gen.writeString(value);
            }
        }
    }
}
