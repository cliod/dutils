package com.wobangkj.bean;

import com.fasterxml.jackson.annotation.JsonView;
import com.wobangkj.api.ValueWrapper;

/**
 * 存储值
 *
 * @author cliod
 * @since 7/18/20 10:30 AM
 */
public abstract class Var<T> implements ValueWrapper<T> {

    protected static String keyName = "value";
    @JsonView
    private final Maps<String, T> value = Maps.of(keyName, null);

    public Var(T t) {
        value.put(keyName, t);
    }

    static void setKeyName(String keyName) {
        Var.keyName = keyName;
    }

    public T getValue() {
        return value.get(keyName);
    }

    public void setValue(T val) {
        value.put(keyName, val);
    }

    @Override
    public T value() {
        return getValue();
    }

    /**
     * 重命名
     *
     * @param oldName 旧key
     * @param newName 新key
     */
    public void rename(String oldName, String newName) {
        T obj = value.rem(oldName);
        value.put(newName, obj);
    }
}
