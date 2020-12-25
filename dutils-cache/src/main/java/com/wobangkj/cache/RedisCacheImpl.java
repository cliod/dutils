package com.wobangkj.cache;

import org.jetbrains.annotations.NotNull;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.Set;

/**
 * redis cache
 *
 * @author cliod
 * @since 6/22/20 10:16 AM
 */
public class RedisCacheImpl implements Cacheables {

	private final RedisTemplate<Object, Object> redisTemplate;

	public RedisCacheImpl(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取包装对象
	 *
	 * @param key 键
	 * @return 值
	 */
	@Override
	public @NotNull ValueWrapper get(@NotNull Object key) {
		return new SimpleValueWrapper(this.redisTemplate.opsForValue().get(key));
	}

	/**
	 * put内容
	 *
	 * @param key    键
	 * @param value  值
	 * @param timing 存储时间
	 */
	@Override
	public void set(Object key, Object value, Timing timing) {
		this.redisTemplate.opsForValue().set(key, value, timing.getTime(), timing.getUnit());
	}

	/**
	 * 删除
	 *
	 * @param key 键
	 */
	@Override
	public void del(Object key) {
		this.redisTemplate.delete(key);
	}

	/**
	 * 清空
	 */
	@Override
	public void clear() {
		Set<Object> keys = this.redisTemplate.keys("*");
		if (Objects.isNull(keys) || keys.isEmpty()) {
			return;
		}
		this.redisTemplate.delete(keys);
	}

	/**
	 * Return the underlying native cache provider.
	 *
	 * @return the underlying native cache provider.
	 */
	@Override
	public @NotNull Object getNativeCache() {
		return this.redisTemplate;
	}

	/**
	 * Return the cache name.
	 *
	 * @return the cache name.
	 */
	@Override
	public @NotNull String getName() {
		return "redis_cache";
	}
}
