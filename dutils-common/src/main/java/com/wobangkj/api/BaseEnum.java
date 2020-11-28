package com.wobangkj.api;

import com.wobangkj.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一枚举接口
 *
 * @author cliod
 * @since 19-7-4
 */
@Deprecated
public interface BaseEnum extends Session {
	/**
	 * 获取所有
	 *
	 * @return 数组
	 */
	@Deprecated
	default BaseEnum[] list() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取code
	 *
	 * @return code
	 */
	Integer getCode();

	/**
	 * 获取msg
	 *
	 * @return msg
	 */
	String getMsg();

	/**
	 * 序列化,转成字符串
	 *
	 * @return Json
	 */
	@Override
	String toString();

	/**
	 * 转化为对象(默认Map)
	 *
	 * @return map
	 */
	@Override
	default Object toObject() {
		Map<String, Object> map = new HashMap<>(4);
		map.put("code", this.getCode());
		map.put("msg", this.getMsg());
		return map;
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
