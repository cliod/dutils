package com.wobangkj.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 对时间的json序列化
 *
 * @author cliod
 * @since 8/29/20 9:49 AM
 */
public class JacksonInstantSerializer extends JsonSerializer<Instant> {

	static {
		Locale.setDefault(Locale.CHINA);
	}

	private final DateTimeFormatter formatter;

	public JacksonInstantSerializer(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}

	public JacksonInstantSerializer(String pattern) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	/**
	 * 时间序列化格式
	 */
	@Override
	public void serialize(Instant arg0, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (null != arg0) {
			gen.writeString(arg0.atZone(ZoneId.systemDefault()).format(formatter));
		}
	}
}
