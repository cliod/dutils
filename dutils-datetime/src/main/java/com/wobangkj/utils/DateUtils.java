package com.wobangkj.utils;

import com.wobangkj.enums.Format;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
	public static final Map<Object, SimpleDateFormat> DATA_FORMAT;
	public static final Map<Object, DateTimeFormatter> FORMATTER;

	/**
	 * 一天的MilliSecond
	 */
	public final static long DAY_MILLI = 24 * 60 * 60 * 1000;
	public final static int LEFT_OPEN_RIGHT_OPEN = 1;
	public final static int LEFT_CLOSE_RIGHT_OPEN = 2;
	public final static int LEFT_OPEN_RIGHT_CLOSE = 3;
	public final static int LEFT_CLOSE_RIGHT_CLOSE = 4;
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
		DATA_FORMAT = new HashMap<>(16);
		SimpleDateFormat date_format;
		for (Format value : Format.values()) {
			date_format = new SimpleDateFormat(value.getPattern());
			DATA_FORMAT.put(value, date_format);
			DATA_FORMAT.put(value.getPattern(), date_format);
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
		return FORMATTER.get(format).format(LocalDateTime.now());
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
}
