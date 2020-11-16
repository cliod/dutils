package com.wobangkj.bean;

import com.wobangkj.api.asserts.Assert;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * map工具类
 *
 * @author cliod
 * @since 7/1/20 4:05 PM
 */
public abstract class Maps<K, V> extends HashMap<K, V> implements Map<K, V> {

	public Maps(int initialCapacity) {
		super(initialCapacity);
	}

	public Maps() {
		this(4);
	}

	public Maps(Map<? extends K, ? extends V> m) {
		super(m);
	}

	public static <K, V> @NotNull Maps<K, V> of(K k, V v) {
		Assert.notNull(k, "key对象不能为空");
		return new Maps<K, V>(16) {{
			put(k, v);
		}};
	}

	public static <K, V> @NotNull Maps<K, V> of(Map<K, V> map) {
		return new Maps<K, V>(map) {
		};
	}

	public static @NotNull Maps<String, Object> to(String k, Object v) {
		Assert.notNull(k, "key对象不能为空");
		return new Maps<String, Object>(16) {{
			put(k, v);
		}};
	}

	public Maps<K, V> set(K k, V v) {
		if (!Objects.isNull(v) && !Objects.isNull(k)) {
			put(k, v);
		}
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
	public V pop(K k) {
		return remove(k);
	}

	public @NotNull Object readResolve() throws Exception {
		return this.getClass().getConstructor().newInstance();
	}

	/**
	 * 克隆
	 *
	 * @return a shallow copy of this map
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Maps<K, V> clone() {
		return Maps.of((Map<K, V>) super.clone());
	}
}
