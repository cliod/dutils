package com.wobangkj.bean;

import com.wobangkj.api.IRes;
import com.wobangkj.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 单值存储
 *
 * @author cliod
 * @since 7/18/20 10:50 AM
 */
public class Single<T> extends Var<T> implements IRes {

	public static final String VALUE_NAME = "value";
	protected final Maps<String, T> data;

	public Single(T t) {
		data = Maps.of(VALUE_NAME, t);
	}

	public Single(String keyName, T v) {
		this.data = Maps.of(keyName, v);
	}

	/**
	 * 静态方法
	 *
	 * @param value 值
	 * @param <T>   类型
	 * @return 结果
	 */
	public static <T> @NotNull Single<T> of(T value) {
		return new Single<>(value);
	}

	/**
	 * 使用新的key名
	 *
	 * @param keyName 键名
	 * @param value   值
	 * @param <T>     泛型
	 * @return 结果
	 */
	public static <T> @NotNull Single<T> of(String keyName, T value) {
		return new Single<>(keyName, value);
	}

	@Override
	public T value() {
		return data.get(VALUE_NAME);
	}

	/**
	 * Gets a result.
	 *
	 * @return a result
	 */
	@Override
	public T get() {
		return this.value();
	}

	@Override
	protected void value(T val) {
		data.put(VALUE_NAME, val);
	}

	/**
	 * 转成Res[Map]对象
	 *
	 * @return Map
	 */
	@Override
	public Res toRes() {
		return Res.from(this.data);
	}

	/**
	 * 转成对象
	 *
	 * @return obj
	 */
	@Override
	public Object toObject() {
		return this.toRes();
	}

	/**
	 * 对象转成json
	 *
	 * @return json
	 */
	@Override
	public String toJson() {
		return JsonUtils.toJson(this.data);
	}
}
