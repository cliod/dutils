package com.wobangkj.bean;

import com.wobangkj.api.asserts.Assert;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * map工具类
 *
 * @author cliod
 * @since 7/1/20 4:05 PM
 */
public abstract class LinkMaps<K, V> extends LinkedHashMap<K, V> implements Map<K, V> {

    public LinkMaps(int initialCapacity) {
        super(initialCapacity);
    }

    public LinkMaps() {
        this(16);
    }

    public LinkMaps(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public static <K, V> @NotNull LinkMaps<K, V> of(K k, V v) {
        Assert.notNull(k, "key对象不能为空");
        return new LinkMaps<K, V>(16) {
            {
                put(k, v);
            }

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return false;
            }
        };
    }

    public static @NotNull LinkMaps<String, Object> to(String k, Object v) {
        Assert.notNull(k, "key对象不能为空");
        return new LinkMaps<String, Object>(16) {
            {
                put(k, v);
            }

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
                return false;
            }
        };
    }

    public LinkMaps<K, V> set(K k, V v) {
        if (!Objects.isNull(v) && !Objects.isNull(k)) {
            put(k, v);
        }
        return this;
    }

    public LinkMaps<K, V> add(K k, V v) {
        put(k, v);
        return this;
    }

    public LinkMaps<K, V> del(K k) {
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

    /**
     * 是否删除最末的数据
     *
     * @param eldest 最末节点
     * @return 是否删除
     */
    @Override
    protected abstract boolean removeEldestEntry(Entry<K, V> eldest);
}
