package com.wobangkj.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wobangkj.annotation.DateFormat;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 序列化器
 *
 * @author cliod
 * @since 7/9/20 3:33 PM
 */
public abstract class Serializer {
    /**
     * 生成objectMapper
     *
     * @return objectMapper 对象
     */
    public final @NotNull ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //设置null值为""
        objectMapper.getSerializerProvider().setNullValueSerializer(new JacksonNullSerializer());
        //这个设置会覆盖字段注解
        objectMapper.setDateFormat(new SimpleDateFormat(com.wobangkj.enums.Format.DATETIME_DEFAULT.getPattern()));
        //设置全局日期格式同时允许 DateTimeFormat 注解,默认取JsonFormat
        //其次取DateTimeFormat,都取不到用默认的
        objectMapper.setAnnotationIntrospector(this.getAnnotationIntrospector());
        this.process(objectMapper);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    public AnnotationIntrospector getAnnotationIntrospector() {
        return new JacksonAnnotationIntrospector() {
            @Override
            public Object findSerializer(Annotated a) {
                if (a instanceof AnnotatedMethod) {
                    AnnotatedElement m = a.getAnnotated();
                    JsonFormat jf = m.getAnnotation(JsonFormat.class);
                    DateFormat dtf = m.getAnnotation(DateFormat.class);
                    if (jf != null) {
                        if (!com.wobangkj.enums.Format.DATETIME_DEFAULT.getPattern().equals(jf.pattern())) {
                            return new JacksonDateSerializer(jf.pattern());
                        }
                    } else if (dtf != null) {
                        if (!com.wobangkj.enums.Format.FORMAT_DATETIME_DEFAULT.getPattern().equals(dtf.pattern())) {
                            return new JacksonDateSerializer(dtf.pattern());
                        }
                    }
                }
                return super.findSerializer(a);
            }
        };
    }

    /**
     * 额外处理
     *
     * @param objectMapper 设置参数
     */
    public void process(ObjectMapper objectMapper) {
    }
}

/**
 * 对空值的序列化
 */
class JacksonNullSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, @NotNull JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value instanceof String)
            gen.writeString("");
        else if (value instanceof Number) {
            gen.writeNumber(0);
        } else {
            gen.writeObject(value);
        }
    }
}

/**
 * 对时间的json序列化
 */
class JacksonDateSerializer extends JsonSerializer<Date> {
    /**
     * 时间序列化格式
     */
    private final String format;

    public JacksonDateSerializer(String format) {
        this.format = format;
    }

    public void serialize(Date arg0, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (null != arg0) {
            gen.writeString(arg0.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(format)));
        }
    }
}
