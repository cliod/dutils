package com.wobangkj.api;

/**
 * 可反序列化的
 *
 * @author cliod
 * @since 9/10/20 10:42 AM
 */
@FunctionalInterface
public interface Deserializer extends Mappable<String, Object> {
	/**
	 * 反序列化为对象
	 *
	 * @return 结果对象
	 */
	@Deprecated
	default Object toObject() {
		return this.toMap();
	}

	/**
	 * 反序列化
	 *
	 * @param json json字符串
	 * @return 对象
	 */
	default Object toObject(CharSequence json) {
		return this;
	}
}
