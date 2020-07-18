package com.wobangkj.bean;

import org.jetbrains.annotations.NotNull;

/**
 * 单值存储
 *
 * @author cliod
 * @since 7/18/20 10:50 AM
 */
public class Single<T> extends Var<T> {

    public Single(T t) {
        super(t);
    }

    public static <T> @NotNull Single<T> of(T value) {
        return new Single<>(value);
    }

    /**
     * 使用新的key名
     *
     * @param key   键
     * @param value 值
     * @param <T>   类型
     * @return 结果
     */
    public static <T> @NotNull Single<T> of(String key, T value) {
        Single<T> single = new Single<>(value);
        single.rename(keyName, key);
        setKeyName(key);
        return single;
    }
}
