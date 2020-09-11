package com.wobangkj.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 * package : com.wobangkj.api
 */
public interface EnumType extends SessionSerializable {

	/**
	 * 获取code
	 *
	 * @return code
	 */
	Integer getCode();

	/**
	 * 获取描述
	 *
	 * @return 描述
	 */
	String getDesc();

	/**
	 * 根据数值获取对象
	 *
	 * @param code 数值
	 * @return 对象
	 */
	EnumType get(Integer code);

	/**
	 * 根据名字或者描述获取对象
	 *
	 * @param name 名字或者描述
	 * @return 对象
	 */
	@Deprecated
	default EnumType get(String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 转化为对象(默认Map)
	 *
	 * @return map
	 */
	@Override
	default Object toObject() {
		Map<String, Object> map = new HashMap<>();
		map.put("code", this.getCode());
		map.put("desc", this.getDesc());
		return map;
	}
}
