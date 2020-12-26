package com.wobangkj.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 默认实现
 *
 * @author cliod
 * @since 8/29/20 9:52 AM
 */
public class DefaultSerializer extends Serializer {

	private static final DefaultSerializer SERIALIZER = new DefaultSerializer(format());
	private final SimpleDateFormat format;

	public static DefaultSerializer getInstance() {
		return SERIALIZER;
	}

	public DefaultSerializer(SimpleDateFormat format) {
		this.format = format;
	}

	@Override
	public void process(@NotNull ObjectMapper objectMapper) {
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		//设置null值为""
		objectMapper.getSerializerProvider().setNullValueSerializer(new JacksonNullSerializer());
		//这个设置会覆盖字段注解
		objectMapper.setDateFormat(format);
		//设置全局日期格式同时允许 DateTimeFormat 注解,默认取JsonFormat
		//其次取DateTimeFormat,都取不到用默认的
		objectMapper.setAnnotationIntrospector(this.getAnnotationIntrospector());
		// 时区设置为当前时区
		objectMapper.setTimeZone(TimeZone.getDefault());
	}
}