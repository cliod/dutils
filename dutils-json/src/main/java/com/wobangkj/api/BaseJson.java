package com.wobangkj.api;

import java.util.List;
import java.util.Map;

/**
 * Json序列化
 *
 * @author cliod
 * @version 1.0
 * @since 2020-12-26 10:44:20
 */
public abstract class BaseJson implements IJson {
	/**
	 * java对象转成json字符串
	 *
	 * @param obj 对象
	 * @return json
	 */
	@Override
	public abstract String toJson(Object obj);

	/**
	 * json字符串转指定类型java对象
	 *
	 * @param str  json字符串
	 * @param type 类型
	 * @param <T>  泛型
	 * @return java对象
	 */
	@Override
	public abstract <T> T toObject(CharSequence str, Class<T> type);

	/**
	 * json字符串转指定类型java对象
	 *
	 * @param str  json字符串
	 * @param type 类型
	 * @param <T>  泛型
	 * @return java对象
	 */
	public <T> T fromJson(CharSequence str, Class<T> type) {
		return this.toObject(str, type);
	}

	/**
	 * json字符串转java对象
	 *
	 * @param str 字符串
	 * @return java对象
	 */
	@Override
	public Object toObject(CharSequence str) {
		return this.toMap(str);
	}

	/**
	 * 转Map对象
	 *
	 * @param str json字符串
	 * @return 对象图列表
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> toMap(CharSequence str) {
		return this.toObject(str, Map.class);
	}

	/**
	 * 转List对象
	 *
	 * @param str json字符串
	 * @param <T> 泛型
	 * @return 对象列表
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> toList(CharSequence str) {
		return this.toObject(str, List.class);
	}
}
