package com.wobangkj.api;

import java.util.HashMap;

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

	@Override
	default Object toObject() {
		return new HashMap<String, Object>() {{
			put("value", value());
		}};
	}
}