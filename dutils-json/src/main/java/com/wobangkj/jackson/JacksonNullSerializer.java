package com.wobangkj.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 对空值的序列化
 *
 * @author cliod
 * @since 8/29/20 9:51 AM
 */
public class JacksonNullSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, @NotNull JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeObject(value);
	}
}
