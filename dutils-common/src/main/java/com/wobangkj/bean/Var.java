package com.wobangkj.bean;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.wobangkj.api.ValueWrapper;

/**
 * 存储值
 *
 * @author cliod
 * @since 7/18/20 10:30 AM
 */
public abstract class Var<T> implements ValueWrapper<T> {
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
}
