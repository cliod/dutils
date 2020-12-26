package com.wobangkj.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wobangkj.api.BaseJson;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Gson实现
 *
 * @author cliod
 * @version 1.0
 * @since 2020-12-26 10:56:59
 */
public class GsonJsonImpl extends BaseJson {

	@Getter
	@Setter
	private Gson gson;

	public GsonJsonImpl() {
		GsonBuilder builder = new GsonBuilder()
				.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC);
		this.gson = builder.create();
	}

	public GsonJsonImpl(GsonBuilder gsonBuilder) {
		this.gson = gsonBuilder.create();
	}

	public GsonJsonImpl(Gson gson) {
		this.gson = gson;
	}

	/**
	 * java对象转成json字符串
	 *
	 * @param obj 对象
	 * @return json
	 */
	@Override
	public String toJson(Object obj) {
		return this.gson.toJson(obj);
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
		return this.gson.fromJson(json, type);
	}

	/**
	 * 转Map对象
	 *
	 * @param str json字符串
	 * @return 对象图列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> toMap(CharSequence str) {
		String json = str.toString();
		return this.gson.fromJson(json, HashMap.class);
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
		return this.gson.fromJson(json, ArrayList.class);
	}
}
