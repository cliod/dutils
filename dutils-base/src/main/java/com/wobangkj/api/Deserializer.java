package com.wobangkj.api;

/**
 * 可反序列化的
 *
 * @author cliod
 * @since 9/10/20 10:42 AM
 */
public interface Deserializer {
	/**
	 * 反序列化
	 *
	 * @return 结果对象
	 */
	Object toObject();
}
