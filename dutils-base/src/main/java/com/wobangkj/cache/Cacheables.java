package com.wobangkj.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 缓存
 *
 * @author cliod
 * @since 6/21/22 10:06 AM
 */
public interface Cacheables extends Cache {
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
	 * @param key    键
	 * @param value  值
	 * @param timing 存储时间
	 */
	void set(Object key, Object value, Timing timing);

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
		return Optional.of(get(key)).orElse(new SimpleValueWrapper(null)).get();
	}

	/**
	 * 删除
	 *
	 * @param key 键
	 */
	void del(Object key);

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

	/**
	 * 时间单位
	 */
	@Getter
	@Setter
	class Timing {

		private long time;
		private TimeUnit unit;
		private transient LocalDateTime deadline;

		/**
		 * 从秒获取对象
		 *
		 * @param time 秒
		 * @return 对象
		 */
		public static @NotNull Timing ofSecond(long time) {
			return of(time, TimeUnit.SECONDS);
		}

		/**
		 * 从分钟获取对象
		 *
		 * @param time 分钟
		 * @return 对象
		 */
		public static @NotNull Timing ofMinutes(long time) {
			return of(time, TimeUnit.MINUTES);
		}

		/**
		 * 从日获取对象
		 *
		 * @param time 日,时长
		 * @return 对象
		 */
		public static @NotNull Timing ofDay(long time) {
			return of(time, TimeUnit.DAYS);
		}

		/**
		 * 从小时获取对象
		 *
		 * @param time 小时
		 * @return 对象
		 */
		public static @NotNull Timing ofHour(long time) {
			return of(time, TimeUnit.HOURS);
		}

		/**
		 * 从时间获取对象
		 *
		 * @param time 时间
		 * @param unit 单位
		 * @return 对象
		 */
		public static @NotNull Timing of(long time, TimeUnit unit) {
			Timing timing = new Timing();
			timing.setTime(time);
			timing.setUnit(unit);
			timing.setDeadline(LocalDateTime.now().plus(time, toChronoUnit(unit)));
			return timing;
		}

		public static ChronoUnit toChronoUnit(@NotNull TimeUnit timeUnit) {
			switch (timeUnit) {
				case NANOSECONDS:
					return ChronoUnit.NANOS;
				case MICROSECONDS:
					return ChronoUnit.MICROS;
				case MILLISECONDS:
					return ChronoUnit.MILLIS;
				case SECONDS:
					return ChronoUnit.SECONDS;
				case MINUTES:
					return ChronoUnit.MINUTES;
				case HOURS:
					return ChronoUnit.HOURS;
				case DAYS:
					return ChronoUnit.DAYS;
				default:
					throw new AssertionError();
			}
		}

		private void setDeadline(LocalDateTime deadline) {
			this.deadline = deadline;
		}
	}
}
