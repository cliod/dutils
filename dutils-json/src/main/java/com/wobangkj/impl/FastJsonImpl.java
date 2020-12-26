package com.wobangkj.impl;

import com.alibaba.fastjson.JSON;
import com.wobangkj.api.BaseJson;

import java.util.List;
import java.util.Map;

/**
 * FastJson实现
 *
 * @author cliod
 * @version 1.0
 * @since 2020-12-26 10:57:23
 */
public class FastJsonImpl extends BaseJson {
	/**
	 * java对象转成json字符串
	 *
	 * @param obj 对象
	 * @return json
	 */
	@Override
	public String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * json字符串转指定类型java对象
	 *
	 * @param str  json字符串
	 * @param type 类型
	 * @return java对象
	 */
	@Override
	public <T> T toObject(CharSequence str, Class<T> type) {
		String json = str.toString();
		return JSON.parseObject(json, type);
	}

	/**
	 * json字符串转java对象
	 *
	 * @param str 字符串
	 * @return java对象
	 */
	@Override
	public Object toObject(CharSequence str) {
		String json = str.toString();
		return JSON.parse(json);
	}

	/**
	 * 转Map对象
	 *
	 * @param str json字符串
	 * @return 对象图列表
	 */
	@Override
	public Map<String, Object> toMap(CharSequence str) {
		String json = str.toString();
		return JSON.parseObject(json);
	}

	/**
	 * 转List对象
	 *
	 * @param str json字符串
	 * @return 对象列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> toList(CharSequence str) {
		String json = str.toString();
		return (List<T>) JSON.parseArray(json);
	}
}
