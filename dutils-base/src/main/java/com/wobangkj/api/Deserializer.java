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
}
