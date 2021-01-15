package com.wobangkj.bean;

import com.wobangkj.api.IRes;
import com.wobangkj.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 两个值存储
 *
 * @author cliod
 * @since 7/18/20 11:23 AM
 */
public class Pair<K, V> extends Var<Object> implements IRes {

	public static final String KEY_NAME = "key";
	public static final String VALUE_NAME = "value";
	protected final Maps<String, Object> data;

	public Pair(K k, V v) {
		data = Maps.of(KEY_NAME, (Object) k).add(VALUE_NAME, v);
	}

	public Pair(String keyName, K k, String valueName, V v) {
		data = Maps.of(keyName, (Object) k).add(valueName, v);
	}

	/**
	 * 使用新的key名
	 *
	 * @param key   键
	 * @param value 值
	 * @param <T>   类型
	 * @return 结果
	 */
	public static <K, T> @NotNull Pair<K, T> of(K key, T value) {
		return new Pair<>(key, value);
	}

	/**
	 * 使用新的key名
	 *
	 * @param key   键
	 * @param value 值
	 * @param <T>   类型
	 * @return 结果
	 */
	public static <K, T> @NotNull Pair<K, T> of(String keyName, K key,String valueName, T value) {
		return new Pair<>(keyName, key,valueName, value);
	}

	@SuppressWarnings("unchecked")
	public K getKey() {
		return (K) data.get(KEY_NAME);
	}

	public void setKey(K key) {
		data.put(KEY_NAME, key);
	}

	@SuppressWarnings("unchecked")
	public V getValue() {
		return (V) data.get(VALUE_NAME);
	}

	public void setValue(V value) {
		data.put(VALUE_NAME, value);
	}

	@Override
	public Object value() {
		return data.get(KEY_NAME);
	}

	/**
	 * Gets a result.
	 *
	 * @return a result
	 */
	@Override
	public Object get() {
		return null;
	}

	@Override
	protected void value(Object val) {
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
