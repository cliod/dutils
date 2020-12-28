package com.wobangkj.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wobangkj.api.BaseJson;
import com.wobangkj.impl.GsonJsonImpl;
import com.wobangkj.impl.JacksonJsonImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * json 工具
 *
 * @author cliod
 * @since 6/13/20 13:06 PM
 */
public class JsonUtils {

	/**
	 * 0jackson，1gson，2fastjson
	 */
	@Setter
	private static int flat = 0;
	@Setter
	@Getter
	private static BaseJson JSON = new JacksonJsonImpl();

	@Deprecated
	public static @NotNull ObjectMapper getJackson() {
		if (flat != 0) {
			throw new UnsupportedOperationException("不存在此对象");
		}
		return ((JacksonJsonImpl) JSON).getObjectMapper();
	}

	@Deprecated
	public static @NotNull Gson getGoogleJson() {
		if (flat != 1) {
			throw new UnsupportedOperationException("不存在此对象");
		}
		return ((GsonJsonImpl) JSON).getGson();
	}

	@Deprecated
	public static void setObjectMapper(@NotNull ObjectMapper objectMapper) {
		setFlat(0);
		((JacksonJsonImpl) JSON).setObjectMapper(objectMapper);
	}

	@Deprecated
	public static void setGsonBuilder(@NotNull GsonBuilder gsonBuilder) {
		setGson(gsonBuilder.create());
	}

	@Deprecated
	public static void setGson(@NotNull Gson gson) {
		setFlat(1);
		((GsonJsonImpl) JSON).setGson(gson);
	}

	/**
	 * json转javabean
	 *
	 * @param json  json字符串
	 * @param clazz 类
	 * @param <T>   类型
	 * @return java对象
	 */
	@SneakyThrows
	public static <T> T fromJson(String json, Class<T> clazz) {
		return JSON.toObject(json, clazz);
	}

	/**
	 * json转java对象(Map)
	 *
	 * @param json json字符串
	 * @return Map对象
	 */
	public static Object toObject(String json) {
		return toMap(json);
	}

	/**
	 * json转java对象(Map)
	 *
	 * @param json json字符串
	 * @return Map对象
	 */
	@SneakyThrows
	public static Map<String, Object> toMap(String json) {
		return JSON.toMap(json);
	}

	/**
	 * json转java列表(List)
	 *
	 * @param <T>  类型
	 * @param json json字符串
	 * @return List::Map对象
	 */
	@SneakyThrows
	public static <T> List<T> toList(String json) {
		return JSON.toList(json);
	}

	/**
	 * java对象转json字符串
	 *
	 * @param obj 对象
	 * @return 字符串
	 */
	@SneakyThrows
	public static String toJson(Object obj) {
		return JSON.toJson(obj);
	}
}
