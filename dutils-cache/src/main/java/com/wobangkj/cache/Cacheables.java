package com.wobangkj.cache;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.Cache;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * 缓存
 *
 * @author cliod
 * @since 6/21/22 10:06 AM
 */
public interface Cacheables extends Cache, Cacheable {
	@Override
	default Object take(Object key) {
		return this.obtain(key);
	}

	/**
	 * 获取包装对象
	 *
	 * @param key 键
	 * @return 值
	 */
	@Override
	@NotNull ValueWrapper get(@NotNull Object key);

	/**
	 * put内容
	 *
	 * @param key   键
	 * @param value 值
	 */
	@Override
	default void put(@NotNull Object key, Object value) {
		this.set(key, value);
	}

	/**
	 * 返回此缓存将指定键映射到的值，并在必要时从valueLoader获得该值。此方法为常规的“如果已缓存，则返回；否则创建，缓存并返回”模式提供了简单的替代方法。可能的话，实现应确保加载操作是同步的，以便在对同一键进行并发访问的情况下，仅一次调用指定
	 *
	 * @param key         键
	 * @param valueLoader 调用
	 * @return 值
	 */
	@Override
	@SneakyThrows
	@SuppressWarnings("unchecked")
	default <T> T get(@NotNull Object key, @NotNull Callable<T> valueLoader) {
		valueLoader.call();
		ValueWrapper obj = get(key);
		return (T) obj.get();
	}

	/**
	 * 获取缓存内容
	 *
	 * @param key   键
	 * @param clazz 返回成指定类型
	 * @return 值
	 */
	@Override
	@SuppressWarnings("unchecked")
	default <T> T get(@NotNull Object key, @Nullable Class<T> clazz) {
		ValueWrapper wrapper = get(key);
		Object obj = wrapper.get();
		if (Objects.isNull(obj) || !Objects.equals(clazz, obj.getClass())) {
			return null;
		}
		return (T) obj;
	}

	/**
	 * 获取缓存内容
	 *
	 * @param key 键
	 * @return 值
	 */
	default Object obtain(Object key) {
		return this.get(key).get();
	}

	/**
	 * 删除
	 *
	 * @param key 键
	 */
	@Override
	default void evict(@NotNull Object key) {
		this.del(key);
	}

	/**
	 * 清空
	 */
	@Override
	void clear();

	/**
	 * Return the underlying native cache provider.
	 *
	 * @return the underlying native cache provider.
	 */
	@Override
	@NotNull Object getNativeCache();

	/**
	 * Return the cache name.
	 *
	 * @return the cache name.
	 */
	@Override
	@NotNull String getName();
}
