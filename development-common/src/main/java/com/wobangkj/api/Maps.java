package com.wobangkj.api;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * map工具类
 *
 * @author cliod
 * @since 7/1/20 4:05 PM
 */
public class Maps<K, V> extends HashMap<K, V> implements Map<K, V> {

    public Maps(int initialCapacity) {
        super(initialCapacity);
    }

    public Maps() {
    }

    public Maps(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public static <K, V> @NotNull Maps<K, V> of(K k, V v) {
        Maps<K, V> map = new Maps<>(16);
        map.put(k, v);
        return map;
    }

    public Maps<K, V> set(K k, V v) {
        put(k, v);
        return this;
    }

    public Maps<K, V> add(K k, V v) {
        put(k, v);
        return this;
    }

    public Maps<K, V> del(K k) {
        remove(k);
        return this;
    }

    /**
     * 获取并删除一个键值
     *
     * @param k 键
     * @return 值
     */
    public V rem(K k) {
        return remove(k);
    }
}
