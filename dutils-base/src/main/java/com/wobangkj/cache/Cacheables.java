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

	@Override
	ValueWrapper get(@NotNull Object kry);

	@Override
	default void put(@NotNull Object key, Object value) {
		this.set(key, value);
	}

	default void set(Object key, Object value) {
		this.set(key, value, Timing.ofDay(1));
	}

	default void set(Object key, Object value, long time, TimeUnit unit) {
		this.set(key, value, Timing.of(time, unit));
	}

	void set(Object key, Object value, Timing timing);

	@Override
	@SneakyThrows
	@SuppressWarnings("unchecked")
	default <T> T get(@NotNull Object key, @NotNull Callable<T> valueLoader) {
		valueLoader.call();
		ValueWrapper obj = get(key);
		if (Objects.isNull(obj)) return null;
		return (T) obj.get();
	}

	@Override
	@SuppressWarnings("unchecked")
	default <T> T get(@NotNull Object key, @Nullable Class<T> clazz) {
		ValueWrapper wrapper = get(key);
		if (Objects.isNull(wrapper)) return null;
		Object obj = wrapper.get();
		if (Objects.isNull(obj)) return null;
		if (!Objects.equals(clazz, obj.getClass())) return null;
		return (T) obj;
	}

	default Object obtain(Object key) {
		return Optional.ofNullable(get(key)).orElse(new SimpleValueWrapper(null)).get();
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
	 */
	@Override
	@NotNull Object getNativeCache();

	/**
	 * Return the cache name.
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

		public static @NotNull Timing ofSecond(long time) {
			return of(time, TimeUnit.SECONDS);
		}

		public static @NotNull Timing ofMinutes(long time) {
			return of(time, TimeUnit.MINUTES);
		}

		public static @NotNull Timing ofDay(long time) {
			return of(time, TimeUnit.DAYS);
		}

		public static @NotNull Timing ofHour(long time) {
			return of(time, TimeUnit.HOURS);
		}

		public static @NotNull Timing of(long time, TimeUnit unit) {
			Timing timing = new Timing();
			timing.setTime(time);
			timing.setUnit(unit);
			timing.setDeadline(LocalDateTime.now().plus(timing.getTime(), toChronoUnit(timing.getUnit())));
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
