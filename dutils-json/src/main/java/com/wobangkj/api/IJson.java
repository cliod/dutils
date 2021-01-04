package com.wobangkj.api;

import java.util.Map;

/**
 * 通用封装 Json接口
 *
 * @author cliod
 * @version 1.0
 * @since 2020-12-26 10:39:26
 */
public interface IJson {
	/**
	 * java对象转成json字符串
	 *
	 * @param obj 对象
	 * @return json
	 */
	<T> String toJson(T obj);

	/**
	 * json字符串转指定类型java对象
	 *
	 * @param str  json字符串
	 * @param type 类型
	 * @param <E>  泛型
	 * @return java对象
	 */
	<E> E toObject(CharSequence str, Class<E> type);

	/**
	 * json字符串转java对象
	 *
	 * @param str 字符串
	 * @return java对象
	 */
	default Object toObject(CharSequence str) {
		return this.toObject(str, Map.class);
	}

	/**
	 * java对象转byte数组
	 *
	 * @param obj 对象
	 * @return byte数组
	 */
	default byte[] toJsonBytes(Object obj) {
		return this.toJson(obj).getBytes();
	}
}
