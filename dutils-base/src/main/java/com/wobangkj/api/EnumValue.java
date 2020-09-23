package com.wobangkj.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 值 - 枚举
 *
 * @author cliod
 * @since 7/8/20 3:33 PM
 */
public interface EnumValue<T> extends SessionSerializable {

	/**
	 * 枚举值
	 *
	 * @return 值
	 */
	T value();

	/**
	 * 转成对象
	 *
	 * @return obj
	 */
	@Override
	default Object toObject() {
		Map<String, Object> map = new HashMap<String, Object>(4);
		map.put("value", value());
		return map;
	}
}