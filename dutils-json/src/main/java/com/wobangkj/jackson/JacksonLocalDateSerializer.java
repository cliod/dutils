package com.wobangkj.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 对时间的json序列化
 *
 * @author cliod
 * @since 8/29/20 9:49 AM
 */
public class JacksonLocalDateSerializer extends JsonSerializer<LocalDate> {

	static {
		Locale.setDefault(Locale.CHINA);
	}

	private final DateTimeFormatter formatter;

	public JacksonLocalDateSerializer(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	public JacksonLocalDateSerializer(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	/**
	 * 时间序列化格式
	 */
	@Override
	public void serialize(LocalDate arg0, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (null != arg0) {
			gen.writeString(arg0.format(formatter));
		}
	}
}