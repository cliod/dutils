package com.wobangkj.cache;

import cn.hutool.core.date.DateUtil;

import java.time.Duration;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 缓存
 *
 * @author cliod
 * @since 6/21/22 10:06 AM
 */
public interface Cacheable extends Storable {
	@Override
	default Object take(Object key) {
		return this.get(key);
	}

	@Override
	default void save(Object key, Object value) {
		this.set(key, value);
	}

	/**
	 * 获取内容
	 *
	 * @param key 键
	 * @return 值
	 */
	Object get(Object key);

	/**
	 * put内容
	 *
	 * @param key   键
	 * @param value 值
	 */
	default void set(Object key, Object value) {
		this.set(key, value, Timing.ofDay(1));
	}

	/**
	 * put内容
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时长
	 * @param unit  单位
	 */
	default void set(Object key, Object value, long time, TimeUnit unit) {
		this.set(key, value, Timing.of(time, unit));
	}

	/**
	 * put内容
	 *
	 * @param key      键
	 * @param value    值
	 * @param duration 时长
	 */
	default void set(Object key, Object value, Duration duration) {
		this.set(key, value, Timing.of(duration));
	}

	/**
	 * put内容
	 *
	 * @param key      键
	 * @param value    值
	 * @param expireAt 过期时间
	 */
	default void set(Object key, Object value, Date expireAt) {
		this.set(key, value, Timing.of(expireAt));
	}

	/**
	 * put内容
	 *
	 * @param key      键
	 * @param value    值
	 * @param expireAt 过期时间
	 */
	default void set(Object key, Object value, TemporalAccessor expireAt) {
		this.set(key, value, DateUtil.date(expireAt));
	}

	/**
	 * put内容
	 *
	 * @param key    键
	 * @param value  值
	 * @param timing 存储时间
	 */
	void set(Object key, Object value, Timing timing);

	/**
	 * 删除
	 *
	 * @param key 键
	 */
	void del(Object key);

	/**
	 * 清空
	 */
	void clear();
}
