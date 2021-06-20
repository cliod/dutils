package com.wobangkj.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 值 - 枚举
 *
 * @author cliod
 * @since 7/8/20 3:33 PM
 */
@FunctionalInterface
public interface EnumValue<T> extends ValueWrapper<T>, SessionSerializable {

	/**
	 * 枚举值
	 *
	 * @return 值
	 */
	@Override
	T value();

	/**
	 * 反序列化
	 *
	 * @return 结果对象
	 */
	@Override
	default Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>(4);
		map.put("value", value());
		return map;
	}
}