package com.wobangkj.api;

import com.wobangkj.utils.JsonUtils;

import java.util.HashMap;

/**
 * 统一类型接口
 *
 * @author cliod
 * @since 19-6-22
 */
@Deprecated
public interface BaseType extends Session {

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
	 * 序列化,转成字符串
	 *
	 * @return Json
	 */
	@Override
	String toString();

	/**
	 * 获取所有枚举
	 *
	 * @return 数组
	 */
	@Deprecated
	default BaseType[] list() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取一个
	 *
	 * @param code code
	 * @return 一个
	 */
	default BaseType get(int code) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取一个
	 *
	 * @param name 名字
	 * @return 一个
	 */
	default BaseType get(String name) {
		BaseType[] values = list();
		for (BaseType value : values) {
			if (value.getDesc().equals(name)) {
				return value;
			}
		}
		return values[0];
	}

	/**
	 * 转化为对象(默认Map)
	 *
	 * @return map
	 */
	@Override
	default Object toObject() {
		return new HashMap<String, Object>(4) {{
			put("code", getCode());
			put("desc", getDesc());
		}};
	}

	/**
	 * 序列化,转成Json
	 *
	 * @return Json
	 */
	@Override
	default String toJson() {
		return JsonUtils.toJson(this.toObject());
	}

}
