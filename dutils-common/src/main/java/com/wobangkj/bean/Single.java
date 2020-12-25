package com.wobangkj.bean;

import org.jetbrains.annotations.NotNull;

/**
 * 单值存储
 *
 * @author cliod
 * @since 7/18/20 10:50 AM
 */
public class Single<T> extends Var<T> {

	private static final String VALUE_NAME = "value";
	private final Maps<String, T> data;

	public Single(T t) {
		data = Maps.of(VALUE_NAME, t);
	}

	/**
	 * 使用新的key名
	 *
	 * @param value 值
	 * @param <T>   类型
	 * @return 结果
	 */
	public static <T> @NotNull Single<T> of(T value) {
		return new Single<>(value);
	}

	@Override
	public T value() {
		return data.get(VALUE_NAME);
	}

	@Override
	protected void value(T val) {
		data.put(VALUE_NAME, val);
	}
}
