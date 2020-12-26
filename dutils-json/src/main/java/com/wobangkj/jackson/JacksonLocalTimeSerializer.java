package com.wobangkj.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 对时间的json序列化
 *
 * @author cliod
 * @since 8/29/20 9:50 AM
 */
public class JacksonLocalTimeSerializer extends JsonSerializer<LocalTime> {

	static {
		Locale.setDefault(Locale.CHINA);
	}

	private final DateTimeFormatter formatter;

	public JacksonLocalTimeSerializer(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	public JacksonLocalTimeSerializer(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	/**
	 * 时间序列化格式
	 */
	@Override
	public void serialize(LocalTime arg0, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (null != arg0) {
			gen.writeString(arg0.format(formatter));
		}
	}
}
