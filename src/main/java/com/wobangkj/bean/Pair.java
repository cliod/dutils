package com.wobangkj.bean;

import org.jetbrains.annotations.NotNull;

/**
 * 两个值存储
 *
 * @author cliod
 * @since 7/18/20 11:23 AM
 */
public class Pair<K, V> extends Var<Object> {

    private static final String keyName = "key";
    private static final String valueName = "value";
    private final Maps<String, Object> _data;

    public Pair(K k, V v) {
        _data = Maps.of(keyName, (Object) k).add(valueName, v);
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
        return (K) _data.get(keyName);
    }

    public void setKey(K key) {
        _data.put(keyName, key);
    }

    @SuppressWarnings("unchecked")
    public V getValue() {
        return (V) _data.get(valueName);
    }

    public void setValue(V value) {
        _data.put(valueName, value);
    }

    @Override
    public Object value() {
        return _data.get(keyName);
    }

    @Override
    protected void value(Object val) {
        _data.put(valueName, val);
    }
}
