package com.wobangkj.bean;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.wobangkj.api.IRes;
import com.wobangkj.api.ValueWrapper;

/**
 * 存储值
 *
 * @author cliod
 * @since 7/18/20 10:30 AM
 */
public abstract class Var<T> implements IRes, ValueWrapper<T> {
	/**
	 * 获取值
	 *
	 * @return 值
	 */
	@Override
	@JsonGetter
	public abstract T value();

	/**
	 * 设置值
	 *
	 * @param val 值
	 */
	@JsonSetter
	protected abstract void value(T val);

	/**
	 * 转成Res[Map]对象
	 *
	 * @return Map
	 */
	@Override
	public Res toRes() {
		return Res.of("value", value());
	}

	/**
	 * 反序列化
	 *
	 * @return 结果对象
	 */
	@Override
	public Object toObject() {
		return this.toRes();
	}
}
