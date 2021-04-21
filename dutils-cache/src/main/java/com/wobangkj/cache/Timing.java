package com.wobangkj.cache;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 时间处理
 *
 * @author cliod
 * @version 1.0
 * @since 2021-04-21 14:31:32
 */
@Getter
@Setter
public class Timing {

	private long time;
	private TimeUnit unit;
	private transient LocalDateTime deadline;
	private transient Date expireAt;

	/**
	 * 从毫秒获取对象
	 *
	 * @param time 秒
	 * @return 对象
	 */
	public static @NotNull Timing ofMillis(long time) {
		return of(time, TimeUnit.MILLISECONDS);
	}

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
	 * 从小时获取对象
	 *
	 * @param time 小时
	 * @return 对象
	 */
	public static @NotNull Timing ofHour(long time) {
		return of(time, TimeUnit.HOURS);
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
	 * 从周获取对象
	 *
	 * @param time 周,时长
	 * @return 对象
	 */
	public static @NotNull Timing ofWeek(long time) {
		return of(time * 7, TimeUnit.DAYS);
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

	/**
	 * 从时长获取对象
	 *
	 * @param duration 时间
	 * @return 对象
	 */
	public static @NotNull Timing of(Duration duration) {
		Timing timing = new Timing();
		timing.setTime(duration.toMillis());
		timing.setUnit(TimeUnit.MILLISECONDS);
		timing.setDeadline(LocalDateTime.now().plus(duration));
		return timing;
	}

	public static @NotNull Timing of(Date date) {
		Date now = new Date();
		return of(date.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
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

	protected void setDeadline(LocalDateTime deadline) {
		this.expireAt = Date.from(deadline.atZone(ZoneId.systemDefault()).toInstant());
		this.deadline = deadline;
	}
}
