package com.wobangkj.api;

/**
 * 可序列化的
 *
 * @author cliod
 * @since 9/10/20 10:40 AM
 */
public interface Serializer {
	/**
	 * 序列化
	 *
	 * @return json字符串
	 */
	String toJson();
}
