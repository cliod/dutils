package com.wobangkj.utils;

import com.wobangkj.enums.Format;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.wobangkj.enums.Format.*;

/**
 * 日期工具
 *
 * @author cliod
 * @since 19-7-19
 * com.wobangkj.git.magicked.util
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	/**
	 * 格式转化器
	 */
	public static final Map<Object, SimpleDateFormat> FORMAT;
	public static final Map<Object, DateTimeFormatter> FORMATTER;

	/**
	 * 一天的MilliSecond
	 */
	public final static long DAY_MILLI = 24 * 60 * 60 * 1000;
	public final static int ONE = 1;
	public final static int TWO = 2;
	public final static int THREE = 3;
	public final static int FOUR = 4;
	/**
	 * 比较日期的模式 --只比较日期，不比较时间
	 */
	public final static int COMP_MODEL_DATE = 1;
	/**
	 * 比较日期的模式 --只比较时间，不比较日期
	 */
	public final static int COMP_MODEL_TIME = 2;
	/**
	 * 比较日期的模式 --比较日期，也比较时间
	 */
	public final static int COMP_MODEL_DATETIME = 3;
	static final String CROSSBAR = "-";
	static final String COLON = ":";
	static final String SPACE = " ";
	static final String EMPTY = "";

	static {
		FORMAT = new HashMap<>(16);
		SimpleDateFormat dateFormat;
		for (Format value : Format.values()) {
			dateFormat = new SimpleDateFormat(value.getPattern());
			FORMAT.put(value, dateFormat);
			FORMAT.put(value.getPattern(), dateFormat);
		}

		FORMATTER = new HashMap<>(16);
		DateTimeFormatter formatter;
		for (Format format : values()) {
			formatter = DateTimeFormatter.ofPattern(format.getPattern());
			FORMATTER.put(format, formatter);
			FORMATTER.put(format.getPattern(), formatter);
		}
	}

	public static String getNow() {
		return LocalDate.now().format(FORMATTER.get(DATE_DEFAULT));
	}

	public static String getNowTime() {
		return LocalDateTime.now().format(FORMATTER.get(DATETIME_DEFAULT));
	}

	public static String getNow(String format) {
		return Optional.ofNullable(FORMATTER.get(format)).orElse(DateTimeFormatter.ofPattern(format)).format(LocalDateTime.now());
	}

	public static String getNow(Format format) {
		return FORMATTER.get(format).format(LocalDateTime.now());
	}

	/**
	 * 获取当前时间的周一
	 *
	 * @return 周一
	 */
	public static LocalDate getFirstDayOfThisWeek() {
		return getFirstDayOfWeek(LocalDate.now());
	}

	/**
	 * 获取当前时间的周一
	 *
	 * @param date 时间
	 * @return 周一
	 */
	public static LocalDate getFirstDayOfWeek(@NotNull Temporal date) {
		TemporalField dayOfWeek = WeekFields.of(Locale.getDefault()).dayOfWeek();
		return LocalDate.from(date.with(dayOfWeek, 1));
	}

	/**
	 * 获取当前时间的周一
	 *
	 * @return 月一号
	 */
	public static LocalDate getFirstDayOfThisMonth() {
		return getFirstDayOfMonth(LocalDate.now());
	}

	/**
	 * 获取当前时间的周一
	 *
	 * @param date 时间
	 * @return 月一号
	 */
	public static LocalDate getFirstDayOfMonth(@NotNull Temporal date) {
		return LocalDate.from(date).withDayOfMonth(1);
	}

	/**
	 * 获取当前时间的 >
	 *
	 * @param date 时间
	 * @param of   的 >
	 * @return 结果
	 */
	public static Temporal getFirstDayOf(@NotNull Temporal date, TemporalField of) {
		return date.with(of, 1);
	}

	/**
	 * 获取当前时间的 >
	 *
	 * @param date 时间
	 * @param of   的 >
	 * @return 结果
	 */
	public static Temporal getFirstDayOf(@NotNull Temporal date, TemporalAdjuster of) {
		return date.with(of);
	}

	/**
	 * 指定时间是否在今天
	 *
	 * @param datetime 时间,正常时间或者Long类型时间
	 * @return 是否
	 */
	public static boolean isDatetimeInToday(@NotNull Temporal datetime) {
		return LocalDate.now().isEqual(LocalDate.from(checkDate(datetime, ONE)));
	}

	/**
	 * 指定时间是否在这周
	 *
	 * @param datetime 时间,正常时间或者Long类型时间
	 * @return 是否
	 */
	public static boolean isDatetimeInThisWeek(@NotNull Temporal datetime) {
		LocalDate date = LocalDate.from(checkDate(datetime, ONE));
		LocalDate s = getFirstDayOfWeek(date);
		return s.isBefore(date) && s.plus(7, ChronoUnit.DAYS).isAfter(date);
	}

	/**
	 * 指定时间是否在这周
	 *
	 * @param datetime 时间,正常时间或者Long类型时间
	 * @return 是否
	 */
	public static boolean isDatetimeInThisMonth(@NotNull Temporal datetime) {
		LocalDate date = LocalDate.from(checkDate(datetime, TWO));
		LocalDate now = LocalDate.now();
		LocalDate s = getFirstDayOfMonth(now);
		LocalDate e = now.with(TemporalAdjusters.lastDayOfMonth());
		return s.isBefore(date) && e.isAfter(date);
	}

	/**
	 * 检查并返回日期
	 *
	 * @param temporal 日期
	 * @param code     需要到日1, 只需要到月2,只需要到年3
	 * @return 结果
	 */
	public static Temporal checkDate(Temporal temporal, int code) {
		Temporal res;
		if (temporal instanceof ChronoLocalDate) {
			res = temporal;
		} else if (temporal instanceof ChronoZonedDateTime) {
			res = temporal;
		} else if (temporal instanceof ChronoLocalDateTime) {
			res = temporal;
		} else if (temporal instanceof Instant) {
			res = LocalDateTime.ofInstant((Instant) temporal, ZoneId.systemDefault());
		} else if (temporal instanceof YearMonth) {
			if (code <= ONE) {
				throw new DateTimeException("不支持没有日期的时间");
			} else {
				YearMonth ym = (YearMonth) temporal;
				res = LocalDate.of(ym.getYear(), ym.getMonth(), 1);
			}
		} else if (temporal instanceof Year) {
			if (code <= TWO) {
				throw new DateTimeException("不支持没有日期的时间");
			} else {
				Year y = (Year) temporal;
				res = LocalDate.of(y.getValue(), 1, 1);
			}
		} else {
			throw new DateTimeException("不支持没有日期的时间");
		}
		return res;
	}

	public static String parse(LocalDate localDate, String pattern) {
		return Optional.ofNullable(FORMATTER.get(pattern)).orElse(DateTimeFormatter.ofPattern(pattern)).format(localDate);
	}

	public static String parse(LocalDateTime localDateTime, String pattern) {
		return Optional.ofNullable(FORMATTER.get(pattern)).orElse(DateTimeFormatter.ofPattern(pattern)).format(localDateTime);
	}

	public static String parse(LocalTime localTime, String pattern) {
		return Optional.ofNullable(FORMATTER.get(pattern)).orElse(DateTimeFormatter.ofPattern(pattern)).format(localTime);
	}
}
