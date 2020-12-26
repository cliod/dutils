package com.wobangkj.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对时间的json序列化
 *
 * @author cliod
 * @since 8/29/20 9:48 AM
 */
public class JacksonDateSerializer extends JsonSerializer<Date> {
	/**
	 * 时间序列化格式
	 */
	private final SimpleDateFormat format;

	public JacksonDateSerializer(SimpleDateFormat format) {
		this.format = format;
	}

	public JacksonDateSerializer(String format) {
		this.format = new SimpleDateFormat(format);
	}

	@Override
	public void serialize(Date arg0, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (null != arg0) {
			gen.writeString(format.format(arg0));
		}
	}
}