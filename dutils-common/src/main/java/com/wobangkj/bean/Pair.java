package com.wobangkj.bean;

import org.jetbrains.annotations.NotNull;

/**
 * 两个值存储
 *
 * @author cliod
 * @since 7/18/20 11:23 AM
 */
public class Pair<K, V> extends Var<Object> {

	private static final String KEY_NAME = "key";
	private static final String VALUE_NAME = "value";
	private final Maps<String, Object> data;

	public Pair(K k, V v) {
		data = Maps.of(KEY_NAME, (Object) k).add(VALUE_NAME, v);
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

	@Override
	protected void value(Object val) {
		data.put(VALUE_NAME, val);
	}
}
