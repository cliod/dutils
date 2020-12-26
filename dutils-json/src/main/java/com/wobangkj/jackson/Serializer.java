package com.wobangkj.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AnnotatedElement;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 序列化器
 *
 * @author cliod
 * @since 7/9/20 3:33 PM
 */
public abstract class Serializer {

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat format() {
		return FORMAT;
	}

	public static DateTimeFormatter formatter() {
		return FORMATTER;
	}

	/**
	 * 生成objectMapper
	 *
	 * @return objectMapper 对象
	 */
	public final @NotNull ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		this.process(objectMapper);
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
		simpleModule.addSerializer(LocalDateTime.class, new JacksonLocalDateTimeSerializer(formatter()));
		simpleModule.addSerializer(LocalDate.class, new JacksonLocalDateSerializer(formatter()));
		simpleModule.addSerializer(LocalTime.class, new JacksonLocalTimeSerializer(formatter()));
		simpleModule.addSerializer(Instant.class, new JacksonInstantSerializer(formatter()));
		objectMapper.registerModule(simpleModule);
		return objectMapper;
	}

	public AnnotationIntrospector getAnnotationIntrospector() {
		return new JacksonAnnotationIntrospector() {
			@Override
			public Object findSerializer(Annotated a) {
				if (a instanceof AnnotatedMethod) {
					JsonSerializer<?> serializer = null;
					AnnotatedElement m = a.getAnnotated();
					JsonFormat jf = m.getAnnotation(JsonFormat.class);
					if (jf != null) {
						if (!jf.pattern().isEmpty()) {
							serializer = new JacksonDateSerializer(jf.pattern());
						}
					}
					if (Objects.isNull(serializer)) {
						serializer = new JacksonDateSerializer(format());
					}
					return serializer;
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
	public abstract void process(@NotNull ObjectMapper objectMapper);
}