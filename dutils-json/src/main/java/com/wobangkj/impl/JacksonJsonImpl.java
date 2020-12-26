package com.wobangkj.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wobangkj.api.BaseJson;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Jackson实现
 *
 * @author cliod
 * @version 1.0
 * @since 2020-12-26 10:49:29
 */
public class JacksonJsonImpl extends BaseJson {
	/**
	 * json对象映射
	 */
	@Setter
	@Getter
	private ObjectMapper objectMapper;

	public JacksonJsonImpl() {
		this.objectMapper = new ObjectMapper();
	}

	public JacksonJsonImpl(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * java对象转成json字符串
	 *
	 * @param obj 对象
	 * @return json
	 */
	@Override
	@SneakyThrows
	public String toJson(Object obj) {
		return this.objectMapper.writeValueAsString(obj);
	}

	/**
	 * json字符串转指定类型java对象
	 *
	 * @param str  json字符串
	 * @param type 类型
	 * @return java对象
	 */
	@Override
	@SneakyThrows
	public <T> T toObject(CharSequence str, Class<T> type) {
		String json = str.toString();
		return this.objectMapper.readValue(json, type);
	}

	/**
	 * 转Map对象
	 *
	 * @param str json字符串
	 * @return 对象图列表
	 */
	@Override
	@SneakyThrows
	public Map<String, Object> toMap(CharSequence str) {
		String json = str.toString();
		return this.objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
			@Override
			public Type getType() {
				return Map.class;
			}
		});
	}

	/**
	 * 转List对象
	 *
	 * @param str json字符串
	 * @return 对象列表
	 */
	@Override
	@SneakyThrows
	public <T> List<T> toList(CharSequence str) {
		String json = str.toString();
		return this.objectMapper.readValue(json, new TypeReference<List<T>>() {
			@Override
			public Type getType() {
				return List.class;
			}
		});
	}

	/**
	 * java对象转byte数组
	 *
	 * @param obj 对象
	 * @return byte数组
	 */
	@Override
	@SneakyThrows
	public byte[] toJsonBytes(Object obj) {
		return this.objectMapper.writeValueAsBytes(obj);
	}
}
