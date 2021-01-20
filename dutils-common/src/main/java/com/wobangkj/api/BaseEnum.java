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
public interface BaseEnum extends Session, EnumMsg {
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
	@Deprecated
	default Object toObject() {
		return this.toMap();
	}

	/**
	 * 反序列化
	 *
	 * @return 结果对象
	 */
	@Override
	default Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>(4);
		map.put("code", this.getCode());
		map.put("msg", this.getMsg());
		return map;
	}
}
