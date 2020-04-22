package com.wobangkj.utils;

import com.wobangkj.api.Format;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.wobangkj.api.Format.*;

/**
 * 日期工具
 *
 * @author cliod
 * @since 19-7-19
 * com.wobangkj.git.magicked.util
 */
public class DateTimeUtils extends org.apache.commons.lang.time.DateUtils {
    /**
     * 格式转化器
     */
    public static final Map<Format, SimpleDateFormat> DATA_FORMAT;
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
        DATA_FORMAT = new HashMap<Format, SimpleDateFormat>(16) {{
            for (Format value : Format.values()) {
                put(value, new SimpleDateFormat(value.getPatten()));
            }
        }};
    }

    /**
     * 获取当前时间的周一
     *
     * @param date 时间
     * @return 周一
     */
    @NotNull
    public static Date getThisWeekMonday(@NotNull Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取当前时间的周一
     *
     * @param timestamp 时间戳
     * @return 周一
     */
    @NotNull
    public static Date getThisWeekMonday(long timestamp) {
        return getThisWeekMonday(new Date(timestamp));
    }

    /**
     * 获取当前时间的周一
     *
     * @param datetime 时间字符串
     * @return 周一
     */
    @NotNull
    public static Date getThisWeekMonday(@NotNull String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        if (FORMAT_DATETIME_DEFAULT.getPatten().length() == datetime.length()) {
            return getThisWeekMonday(sdf.parse(datetime, new ParsePosition(0)));
        } else {
            return getThisWeekMonday(sdf.parse(convertDefaultDateTimeStr(datetime), new ParsePosition(0)));
        }
    }

    /**
     * 获取指定时间到当前的天数
     *
     * @param datetime 指定时间
     * @return 天数
     */
    public static long getPassedDayFrom(String datetime) {
        return getDaySub(datetime, getNow(FORMAT_DATETIME_DEFAULT.getPatten()));
    }

    ;

    /**
     * 获取指定时间到当前的周数
     * 注: 以周一结算
     *
     * @param datetime 指定时间
     * @return 周数
     */
    public static long getPassedWeekFrom(String datetime) {
        return (getPassedDayFrom(datetime) / 7 + 1);
    }

    /**
     * 指定时间是否在今天
     *
     * @param datetime 时间,正常时间或者Long类型时间
     * @return 是否
     */
    public static boolean isDatetimeInToday(@NotNull String datetime) {
        long start = getMidnight();
        long end = start + 24 * 3600 * 1000;
        long now;
        if (FORMAT_DATETIME.getPatten().length() == datetime.length()) {
            now = convertToTimestampLong(convertDefaultDateTimeStr(datetime));
        } else {
            //标准格式
            now = convertToTimestampLong(datetime);
        }
        return start < now && now < end;
    }

    /**
     * 指定时间是否在这周
     *
     * @param datetime 时间,正常时间或者Long类型时间
     * @return 是否
     */
    public static boolean isDatetimeInThisWeek(@NotNull String datetime) {
        Date date = getThisWeekMonday(System.currentTimeMillis());
        long start = date.getTime();
        long now = convertToTimestampLong(datetime);
        long end = start + 7 * 24 * 3600000;
        return start < now && now < end;
    }

    /**
     * 指定时间是否在这周
     *
     * @param datetime 时间,正常时间或者Long类型时间
     * @return 是否
     */
    public static boolean isDatetimeInThisMonth(@NotNull String datetime) {
        long len = datetime.length();
        String date;
        String now = getNow(FORMAT_DATE_DEFAULT.getPatten());
        if (len == FORMAT_DATETIME_DEFAULT.getPatten().length() || len == FORMAT_DATE_DEFAULT.getPatten().length()) {
            date = datetime.substring(0, 7);
        } else {
            date = convertDefaultDateTimeStr(datetime).substring(0, 7);
        }
        return now.contains(date);
    }

    public static boolean isDatetimeInThisMonth(long timestamp) {
        String datetime = convertToDefaultDateTimeStr(timestamp);
        return isDatetimeInThisMonth(datetime);
    }

    /**
     * 时间相减得到天数
     *
     * @param dateStr1 开始时间
     * @param dateStr2 结束时间
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String dateStr1, String dateStr2) {
        LocalDate date1 = LocalDate.parse(dateStr1, DateTimeFormatter.ofPattern(FORMAT_DATETIME_DEFAULT.getPatten()));
        LocalDate date2 = LocalDate.parse(dateStr2, DateTimeFormatter.ofPattern(FORMAT_DATETIME_DEFAULT.getPatten()));
        return date1.toEpochDay() - date2.toEpochDay();
    }

    /**
     * 获取午夜12点,当天的开始
     *
     * @return 当天的00:00,昨天的24:00
     */
    public static long getMidnight() {
        String start = getNow(FORMAT_DATE_DEFAULT.getPatten()) + " 00:00:00";
        return convertToTimestampLong(start);
    }

    @NotNull
    public static String getNow(String pattern) {
        return getNow(pattern, LocalDateTime.now());
    }

    @NotNull
    public static String getNow(String pattern, LocalDate localDate) {
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }

    @NotNull
    public static String getNow(String pattern, LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    @NotNull
    public static String getNow(String pattern, LocalTime localTime) {
        return DateTimeFormatter.ofPattern(pattern).format(localTime);
    }

    @NotNull
    public static String getDefaultTimeStr() {
        SimpleDateFormat sdf = DATA_FORMAT.get(FORMAT_TIME_DEFAULT);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 时间戳转时间
     *
     * @param timestamp 时间戳
     * @return 时间
     */
    @NotNull
    public static Date convertTimestampToDate(String timestamp) {
        return new Date(Long.parseLong(timestamp));
    }

    /**
     * 时间戳转时间
     *
     * @param timestamp 时间戳
     * @return 时间字符串
     */
    @NotNull
    public static String convertToDefaultDateTimeStr(String timestamp) {
        return convertToDefaultDateTimeStr(Long.parseLong(timestamp));
    }

    /**
     * 时间戳转时间
     *
     * @param date 时间戳
     * @return 时间字符串
     */
    @NotNull
    public static String convertToDefaultDateTimeStr(@NotNull Date date) {
        return date
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern(FORMAT_DATETIME_DEFAULT.getPatten()));
    }

    /**
     * 时间戳转时间
     *
     * @param timestamp 时间戳
     * @return 时间字符串
     */
    @NotNull
    public static String convertToDefaultDateTimeStr(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern(FORMAT_DATETIME_DEFAULT.getPatten()));
    }

    /**
     * 时间转时间戳
     *
     * @param datetime 时间
     * @return 时间戳字符串
     */
    @NotNull
    public static String convertToTimestampStr(@NotNull String datetime) {
        return Long.toString(convertToTimestampLong(datetime));
    }

    /**
     * 时间转时间戳
     *
     * @param datetime 时间
     * @return 时间戳
     */
    public static long convertToTimestampLong(@NotNull String datetime) {
        SimpleDateFormat sdf;
        if (FORMAT_DATETIME_DEFAULT.getPatten().length() == datetime.length()) {
            sdf = DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT);
        } else {
            sdf = DATA_FORMAT.get(FORMAT_DATE_DEFAULT);
        }
        ParsePosition position = new ParsePosition(0);
        return sdf.parse(datetime, position).getTime();
    }

    /**
     * 时间转日期
     *
     * @param datetime 时间
     * @return 日期
     */
    public static String convertDefaultDateTimeStr(@NotNull String datetime) {
        String result;
        if (datetime.length() == FORMAT_DATE.getPatten().length() || datetime.length() == FORMAT_DATETIME.getPatten().length()) {
            result = datetime.substring(0, 4) + "-" +
                    datetime.substring(4, 6) + "-" +
                    datetime.substring(6, 8);
            if (datetime.length() == FORMAT_DATETIME.getPatten().length()) {
                result += " " +
                        datetime.substring(8, 10) + ":" +
                        datetime.substring(10, 12) + ":" +
                        datetime.substring(12, 14);
            }
        } else {
            SimpleDateFormat sdf = DATA_FORMAT.get(FORMAT_DATETIME);
            Date date = null;
            try {
                date = sdf.parse(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sdf = DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT);
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * 日期时间转为long 型时间
     *
     * @param datetime 日期时间
     * @return long类型时间
     */
    @NotNull
    public static String convertDateTimeStr(@NotNull String datetime) {
        return datetime.replaceAll(CROSSBAR, EMPTY).replaceAll(COLON, EMPTY).replaceAll(SPACE, EMPTY);
    }

    /**
     * 根据日期格式字符串解析日期字符串
     *
     * @param str           日期字符串
     * @param parsePatterns 日期格式字符串
     * @return 解析后日期
     * @throws ParseException 格式转化异常
     */
    public static Date parseDate(String str, String parsePatterns) throws ParseException {
        return parseDate(str, new String[]{parsePatterns});
    }

    /**
     * 根据单位字段比较两个日期
     *
     * @param date      日期1
     * @param otherDate 日期2
     * @param withUnit  单位字段，从Calendar field取值
     * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
     */
    public static int compareDate(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);

        switch (withUnit) {
            case Calendar.YEAR:
                dateCal.clear(Calendar.MONTH);
                otherDateCal.clear(Calendar.MONTH);
            case Calendar.MONTH:
                dateCal.set(Calendar.DATE, 1);
                otherDateCal.set(Calendar.DATE, 1);
            case Calendar.DATE:
                dateCal.set(Calendar.HOUR_OF_DAY, 0);
                otherDateCal.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR:
                dateCal.clear(Calendar.MINUTE);
                otherDateCal.clear(Calendar.MINUTE);
            case Calendar.MINUTE:
                dateCal.clear(Calendar.SECOND);
                otherDateCal.clear(Calendar.SECOND);
            case Calendar.SECOND:
                dateCal.clear(Calendar.MILLISECOND);
                otherDateCal.clear(Calendar.MILLISECOND);
            case Calendar.MILLISECOND:
                break;
            default:
                throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
        return dateCal.compareTo(otherDateCal);
    }

    /**
     * 根据单位字段比较两个时间
     *
     * @param date      时间1
     * @param otherDate 时间2
     * @param withUnit  单位字段，从Calendar field取值
     * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
     */
    public static int compareTime(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);

        dateCal.clear(Calendar.YEAR);
        dateCal.clear(Calendar.MONTH);
        dateCal.set(Calendar.DATE, 1);
        otherDateCal.clear(Calendar.YEAR);
        otherDateCal.clear(Calendar.MONTH);
        otherDateCal.set(Calendar.DATE, 1);
        switch (withUnit) {
            case Calendar.HOUR:
                dateCal.clear(Calendar.MINUTE);
                otherDateCal.clear(Calendar.MINUTE);
            case Calendar.MINUTE:
                dateCal.clear(Calendar.SECOND);
                otherDateCal.clear(Calendar.SECOND);
            case Calendar.SECOND:
                dateCal.clear(Calendar.MILLISECOND);
                otherDateCal.clear(Calendar.MILLISECOND);
            case Calendar.MILLISECOND:
                break;
            default:
                throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
        return dateCal.compareTo(otherDateCal);
    }

    /**
     * 获得当前的日期毫秒
     *
     * @return 毫秒
     */
    public static long nowTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获得当前的时间戳
     *
     * @return 时间戳
     */
    @NotNull
    public static Timestamp nowTimeStamp() {
        return new Timestamp(nowTimeMillis());
    }

    /**
     * yyyy-MM-dd 当前日期
     */
    @NotNull
    public static String getReqDate() {
        return DATA_FORMAT.get(FORMAT_DATE).format(new Date());
    }

    /**
     * yyyy-MM-dd 传入日期
     *
     * @param date 日期
     * @return 字符串
     */
    @NotNull
    public static String getReqDate(Date date) {
        return DATA_FORMAT.get(FORMAT_DATE_DEFAULT).format(date);
    }

    /**
     * yyyyMMdd 传入日期
     *
     * @param date 日期
     * @return 字符串
     */
    @NotNull
    public static String getReqDateTight(Date date) {
        return DATA_FORMAT.get(FORMAT_DATE).format(date);
    }

    /**
     * yyyy-MM-dd 传入的时间戳
     *
     * @param tmp 时间戳
     * @return 字符串
     */
    @NotNull
    public static String TimestampToDateStr(Timestamp tmp) {
        return DATA_FORMAT.get(FORMAT_DATE_DEFAULT).format(tmp);
    }

    /**
     * HH:mm:ss 当前时间
     *
     * @return 字符串
     */
    @NotNull
    public static String getReqTime() {
        return DATA_FORMAT.get(FORMAT_TIME_DEFAULT).format(new Date());
    }

    /**
     * 得到时间戳格式字串
     *
     * @param date 时间
     * @return 字符串
     */
    @NotNull
    public static String getTimeStampStr(Date date) {
        return DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT).format(date);
    }

    /**
     * 得到长日期格式字串
     *
     * @return 字符串
     */
    @NotNull
    public static String getLongDateStr() {
        return DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT).format(new Date());
    }

    /**
     * 时间戳转Long字符串
     *
     * @param time 时间戳
     * @return Long字符串
     */
    @NotNull
    public static String getLongDateStr(Timestamp time) {
        return DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT).format(time);
    }

    /**
     * 得到短日期格式字串
     *
     * @param date 日期
     * @return 字符串
     */
    @NotNull
    public static String getShortDateStr(Date date) {
        return DATA_FORMAT.get(FORMAT_DATE_DEFAULT).format(date);
    }

    @NotNull
    public static String getShortDateStr() {
        return DATA_FORMAT.get(FORMAT_DATE_DEFAULT).format(new Date());
    }

    /**
     * 计算 second 秒后的时间
     *
     * @param date   时间
     * @param second 秒
     * @return 时间
     */
    @NotNull
    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ;
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 计算 minute 分钟后的时间
     *
     * @param date   时间
     * @param minute 分钟
     * @return 时间
     */
    @NotNull
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 计算 hour 小时后的时间
     *
     * @param date 时间
     * @param hour 小时
     * @return 时间
     */
    @NotNull
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 得到day的起始时间点。
     *
     * @param date 时间
     * @return 起始点
     */
    @NotNull
    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到day的终止时间点.
     *
     * @param date 时间
     * @return 终止点
     */
    @NotNull
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 计算 day 天后的时间
     *
     * @param date 日期
     * @param day  天数
     * @return 日期
     */
    @NotNull
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 得到month的终止时间点.
     *
     * @param date 日期
     * @return 月末
     */
    @NotNull
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    @NotNull
    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 365 * year);
        return calendar.getTime();
    }

    @NotNull
    public static Timestamp strToTimestamp(String dateStr) {
        return Timestamp.valueOf(dateStr);
    }

    @NotNull
    public static Timestamp strToTimestamp(Date date) {
        return Timestamp.valueOf(DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT).format(date));
    }

    @NotNull
    public static Timestamp getCurTimestamp() {
        return Timestamp.valueOf(DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT).format(new Date()));
    }

    /**
     * 取得两个日期之间的日数
     *
     * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
     */
    public static long daysBetween(@NotNull Timestamp t1, @NotNull Timestamp t2) {
        return (t2.getTime() - t1.getTime()) / DAY_MILLI;
    }

    /**
     * 返回java.sql.Timestamp型的SYSDATE
     *
     * @return java.sql.Timestamp型的SYSDATE
     */
    @NotNull
    public static Timestamp getSysDateTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
     *
     * @param sDate Date string
     * @return 时间戳
     */
    public static Timestamp toSqlTimestamp(String sDate) {
        if (sDate == null) {
            return null;
        }
        if (sDate.length() != FORMAT_DATE_DEFAULT.getPatten().length()
                && sDate.length() != FORMAT_DATETIME_DEFAULT.getPatten().length()) {
            return null;
        }
        return toSqlTimestamp(sDate,
                sDate.length() == FORMAT_DATE_DEFAULT.getPatten().length()
                        ? FORMAT_DATE_DEFAULT.getPatten()
                        : FORMAT_DATETIME_DEFAULT.getPatten());

    }

    /**
     * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
     *
     * @param sDate Date string
     * @param sFmt  Date format DATE_FORMAT_DATEONLY/FORMAT_DATETIME_DEFAULT.getPatten()
     * @return 时间戳
     */
    public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
        String temp = null;
        if (sDate == null || sFmt == null) {
            return null;
        }
        if (sDate.length() != sFmt.length()) {
            return null;
        }
        if (sFmt.equals(FORMAT_DATETIME_DEFAULT.getPatten())) {
            temp = sDate.replace('/', '-');
            temp = temp + ".000000000";
        } else if (sFmt.equals(FORMAT_DATE_DEFAULT.getPatten())) {
            temp = sDate.replace('/', '-');
            temp = temp + " 00:00:00.000000000";
        } else {
            return null;
        }
        return Timestamp.valueOf(temp);
    }

    /**
     * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
     *
     * @return 系统日期时间
     */
    @NotNull
    public static String getSysDateTimeString() {
        return toString(new Date(System.currentTimeMillis()), DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT));
    }

    /**
     * 根据指定的Format转化java.util.Date到String
     *
     * @param dt   java.util.Date instance
     * @param sFmt Date format , DATE_FORMAT_DATEONLY or FORMAT_DATETIME_DEFAULT.getPatten()
     * @return 字符串
     */
    @NotNull
    public static String toString(Date dt, String sFmt) {
        if (dt == null || sFmt == null || "".equals(sFmt)) {
            return "";
        }
        return toString(dt, new SimpleDateFormat(sFmt));
    }

    /**
     * 利用指定SimpleDateFormat instance转换java.util.Date到String
     *
     * @param dt        java.util.Date instance
     * @param formatter SimpleDateFormat Instance
     * @return 字符串
     */
    @NotNull
    private static String toString(Date dt, @NotNull SimpleDateFormat formatter) {
        return formatter.format(dt);
    }

    /**
     * 转换java.sql.Timestamp到String，格式为YYYY/MM/DD HH24:MI
     *
     * @param dt java.sql.Timestamp instance
     * @return 字符串
     */
    public static String toSqlTimestampString(Timestamp dt) {
        if (dt == null) {
            return null;
        }
        String temp = toSqlTimestampString(dt, FORMAT_DATETIME_DEFAULT.getPatten());
        return temp.substring(0, 16);
    }

    public static String toString(Timestamp dt) {
        return dt == null ? "" : toSqlTimestampString(dt);
    }

    /**
     * 根据指定的格式转换java.sql.Timestamp到String
     *
     * @param dt   java.sql.Timestamp instance
     * @param sFmt Date 格式，DATE_FORMAT_DATE_ONLY/FORMAT_DATETIME_DEFAULT.getPatten()/DATE_FORMAT_SESSION
     * @return 字符串
     */
    public static String toSqlTimestampString(Timestamp dt, String sFmt) {
        String temp = null;
        String out = null;
        if (dt == null || sFmt == null) {
            return null;
        }
        temp = dt.toString();
        // "YYYY/MM/DD // HH24:MI:SS" // YYYY/MM/DD
        if (sFmt.equals(FORMAT_DATETIME_DEFAULT.getPatten()) || sFmt.equals(FORMAT_DATE_DEFAULT.getPatten())) {
            temp = temp.substring(0, sFmt.length());
            out = temp.replace('/', '-');
        }
        return out;
    }

    /**
     * 得到当前日期的星期
     *
     * @return 星期
     */
    public static int getWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Timestamp 格式转换成yyyy-MM-dd timestampToSql(Timestamp 格式转换成yyyy-MM-dd)
     *
     * @param timestamp 时间
     * @return createTimeStr yyyy-MM-dd 时间
     */
    @NotNull
    public static String timestampToStringYMD(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_DEFAULT.getPatten());
        return sdf.format(timestamp);
    }

    /**
     * 判断一个时间是否在某个时间区间内
     *
     * @param now   目标时间
     * @param start 时间区间开始
     * @param end   时间区间结束
     * @param model 区间模式
     * @return 是否在区间内
     */
    public static boolean isBetween(Date now, Date start, Date end, int model) {
        return isBetween(now, start, end, model, LEFT_OPEN_RIGHT_OPEN);
    }

    /**
     * 判断时间是否在制定的时间段之类
     *
     * @param date       需要判断的时间
     * @param start      时间段的起始时间
     * @param end        时间段的截止时间
     * @param interModel 区间的模式
     * @param compModel  比较的模式
     * @return 是否在两个时间之间
     */
    public static boolean isBetween(Date date, Date start, Date end, int interModel, int compModel) {
        if (date == null || start == null || end == null) {
            throw new IllegalArgumentException("日期不能为空");
        }
        SimpleDateFormat format = null;
        switch (compModel) {
            case COMP_MODEL_DATE: {
                format = DATA_FORMAT.get(FORMAT_DATE);
                break;
            }
            case COMP_MODEL_TIME: {
                format = DATA_FORMAT.get(FORMAT_TIME);
                break;
            }
            case COMP_MODEL_DATETIME: {
                format = DATA_FORMAT.get(FORMAT_DATETIME);
                break;
            }
            default: {
                throw new IllegalArgumentException(String.format("日期的比较模式[%d]有误", compModel));
            }
        }
        long dateNumber = Long.parseLong(format.format(date));
        long startNumber = Long.parseLong(format.format(start));
        long endNumber = Long.parseLong(format.format(end));
        switch (interModel) {
            case LEFT_OPEN_RIGHT_OPEN: {
                return dateNumber > startNumber && dateNumber < endNumber;
            }
            case LEFT_CLOSE_RIGHT_OPEN: {
                return dateNumber >= startNumber && dateNumber < endNumber;
            }
            case LEFT_OPEN_RIGHT_CLOSE: {
                return dateNumber > startNumber && dateNumber <= endNumber;
            }
            case LEFT_CLOSE_RIGHT_CLOSE: {
                return dateNumber >= startNumber && dateNumber <= endNumber;
            }
            default: {
                throw new IllegalArgumentException(String.format("日期的区间模式[%d]有误", interModel));
            }
        }
    }

    /**
     * 得到当前周起始时间
     *
     * @param date 时间
     * @return 时间
     */
    @NotNull
    public static Date getWeekStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.WEEK_OF_YEAR);
        int firstDay = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, firstDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到当前周截止时间
     *
     * @param date 时间
     * @return 时间
     */
    @NotNull
    public static Date getWeekEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.WEEK_OF_YEAR);
        int firstDay = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, 8 - firstDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到当月起始时间
     *
     * @param date 时间
     * @return 时间
     */
    @NotNull
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到当前年起始时间
     *
     * @param date 时间
     * @return 时间
     */
    @NotNull
    public static Date getYearStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到当前年最后一天
     *
     * @param date 时间
     * @return 时间
     */
    @NotNull
    public static Date getYearEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 取得月天数
     *
     * @param date 时间
     * @return 天数
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月第一天
     *
     * @param date 时间
     * @return 第一天
     */
    @NotNull
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date 时间
     * @return 最后一天
     */
    @NotNull
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得季度第一天
     *
     * @param date 时间
     * @return 季度第一天
     */
    @NotNull
    public static Date getSeasonStart(Date date) {
        return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
    }

    /**
     * 取得季度最后一天
     *
     * @param date 时间
     * @return 季度最后一天
     */
    @NotNull
    public static Date getSeasonEnd(Date date) {
        return getDayEnd(getLastDateOfMonth(getSeasonDate(date)[2]));
    }

    /**
     * 取得季度月
     *
     * @param date 时间
     * @return 季度
     */
    @NotNull
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date 时间
     * @return 季度
     */
    public static int getSeason(Date date) {
        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 判断输入日期是一个星期中的第几天(星期天为一个星期第一天)
     *
     * @param date 时间
     * @return 星期
     */
    public static int getWeekIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 当前时间的前几天，并且以例如2013/12/09 00:00:00 形式输出
     */
    public static Date subDays(int days) throws ParseException {
        Date date = addDay(new Date(), -days);
        String dateStr = getReqDate(date);
        SimpleDateFormat sdf = DATA_FORMAT.get(FORMAT_DATE_DEFAULT);
        return sdf.parse(dateStr);
    }

    /**
     * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制 如：开始时间和结束时间，不能超出距离当前时间90天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间按
     * @param interval  间隔数
     * @param dateUnit  单位(如：月，日),参照Calendar的时间单位
     * @return 是否超出
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(dateUnit, interval * (-1));
        Date curDate = getDayStart(cal.getTime());
        return getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0;
    }

    /**
     * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间按
     * @param interval  间隔数
     * @return 是否超出
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, interval * (-1));
        Date curDate = getDayStart(cal.getTime());
        return getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0;
    }

    /**
     * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天
     *
     * @param startDateStr 开始时间
     * @param endDateStr   结束时间按
     * @param interval     间隔数
     * @return 是否超出
     */
    public static boolean isOverIntervalLimit(String startDateStr, String endDateStr, int interval) throws ParseException {
        Date startDate;
        Date endDate;
        startDate = DateTimeUtils.parseDate(startDateStr, FORMAT_DATE_DEFAULT.getPatten());
        endDate = DateTimeUtils.parseDate(endDateStr, FORMAT_DATE_DEFAULT.getPatten());
        if (startDate == null || endDate == null) {
            return true;
        }
        return isOverIntervalLimit(startDate, endDate, interval);
    }

    /**
     * 传入时间字符串及时间格式，返回对应的Date对象
     *
     * @param src     时间字符串
     * @param pattern 时间格式
     * @return Date
     */
    public static Date getDateFromString(String src, String pattern) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        return f.parse(src);
    }

    /**
     * 取季度
     *
     * @param date 时间
     * @return 季度
     */
    public static int getQuarter(@NotNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        if (month == 0 || month == 1 || month == 2) {
            return 1;
        } else if (month == 3 || month == 4 || month == 5) {
            return 2;
        } else if (month == 6 || month == 7 || month == 8) {
            return 3;
        } else if (month == 9 || month == 10 || month == 11) {
            return 4;
        } else {
            return 0;
        }
    }

    /**
     * 取得通用日期时间格式字符串
     *
     * @param date 时间
     * @return String
     */
    @NotNull
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT);
        return formatDate(date, FORMAT_DATETIME_DEFAULT.getPatten());
    }

    /**
     * 获取当日的日期格式串
     *
     * @return String
     */
    @NotNull
    public static String nowDate() {
        return formatDate(new Date(), FORMAT_DATE_DEFAULT.getPatten());
    }

    /**
     * 获取当前时间格式串
     *
     * @return String
     */
    @NotNull
    public static String nowTime() {
        return formatDate(new Date(), FORMAT_DATETIME.getPatten());
    }

    /**
     * 取得指定日期格式的字符串
     *
     * @param date 时间
     * @return String
     */
    @NotNull
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获取昨日的日期格式串
     *
     * @return Date
     */
    @NotNull
    public static String getYesterday() {
        return formatDate(getDate(-1), FORMAT_DATE_DEFAULT.getPatten());
    }

    /**
     * 判断当前时间是否在一定的时间范围内
     *
     * @param startTime 开始时间
     * @return 当前是否在
     */
    public static boolean isInBetweenTimes(String startTime, String endTime) {
        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(nowTime);
        return time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0;
    }

    /**
     * 字符转日期
     *
     * @param dateStr 日期
     * @return 日期
     */
    public static Date getDateByStr(String dateStr) {
        SimpleDateFormat formatter = null;
        if (dateStr == null) {
            return null;
        } else if (dateStr.length() == 10) {
            formatter = DATA_FORMAT.get(FORMAT_DATE_DEFAULT);
        } else if (dateStr.length() == 16) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (dateStr.length() >= 19) {
            dateStr = dateStr.substring(0, 19);
            formatter = DATA_FORMAT.get(FORMAT_DATETIME_DEFAULT);
        } else {
            return null;
        }
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取days天之后的时间
     *
     * @param days 天数
     * @return D时间
     */
    @NotNull
    public static Date getDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 日期最大时间：获取当天23:59:59点
     *
     * @param dt 日期
     * @return 最大时间
     */
    @NotNull
    public static Date getMaxTime(Date dt) {

        Date dt1 = null;
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        dt1 = ca.getTime();
        dt1 = DateTimeUtils.getMinTime(dt1);
        ca.setTime(dt1);
        ca.add(Calendar.SECOND, -1);
        dt1 = ca.getTime();
        return dt1;
    }

    /**
     * 日期最小时间：获取当天0点
     *
     * @param dt 日期
     * @return 最小时间
     */
    public static Date getMinTime(Date dt) {
        Date dt1 = null;
        dt1 = DateTimeUtils.getDateByStr(DateTimeUtils.formatDate(dt, FORMAT_DATE_DEFAULT.getPatten()));
        return dt1;
    }

    /**
     * 这个月的最后一天
     *
     * @param date 日期
     * @return 时间
     */
    @NotNull
    public static Date getLastDayOfMonth(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        return setDays(lastDate, lastDay);
    }

    /**
     * 这个月的第一天
     *
     * @param date 日期
     * @return 第一天
     */
    @NotNull
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 上月第一天
     *
     * @return 第一天
     */
    public static Date getPreviousMonthFirstDay() {
        Calendar lastDate = Calendar.getInstance();
        // 设为当前月的1号
        lastDate.set(Calendar.DATE, 1);
        // 减一个月，变为下月的1号
        lastDate.add(Calendar.MONTH, -1);
        return getMinTime(lastDate.getTime());
    }

    /**
     * 上月最后一天
     *
     * @return 上月末
     */
    public static Date getPreviousMonthLastDay() {
        Calendar lastDate = Calendar.getInstance();
        // 设为当前月的1号
        lastDate.set(Calendar.DATE, 1);
        lastDate.add(Calendar.DATE, -1);
        return getMinTime(lastDate.getTime());
    }

    /**
     * 返回天数
     *
     * @param date1 日期
     * @param date2 日期
     * @return 天数
     */
    public static long getDateDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0L;
        }
        return (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2
                .getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 判断两个时间的相差年数
     *
     * @param date1 日期
     * @param date2 日期
     * @return 年书
     */
    public static int getYearDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        int year1 = calendar1.get(Calendar.YEAR);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int year2 = calendar2.get(Calendar.YEAR);

        return Math.abs(year1 - year2);
    }

    /**
     * 获取两个时间的毫秒数
     *
     * @param date1 时间
     * @param date2 时间
     * @return 毫秒差
     */
    public static long getTimeDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0L;
        }
        return (date1.getTime() - date2.getTime()) > 0 ? (date1.getTime() - date2
                .getTime()) : (date2.getTime() - date1.getTime());
    }

    /**
     * 判断两个时间是不是在一个周中
     */
    public static boolean isSameWeekWithToday(Date date) {
        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);
        int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
        // subYear==0,说明是同一年
        if (subYear == 0) {
            return todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR);
        } else if (subYear == 1 && dateCal.get(Calendar.MONTH) == Calendar.DECEMBER && todayCal.get(Calendar.MONTH) == Calendar.JANUARY) {
            return todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR);
        } else if (subYear == -1 && todayCal.get(Calendar.MONTH) == Calendar.DECEMBER && dateCal.get(Calendar.MONTH) == Calendar.JANUARY) {
            return todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR);
        }
        return false;
    }

    /**
     * 获取几天内日期 return 2014-5-4、2014-5-3
     */
    @NotNull
    public static List<String> getLastDays(int countDay) {
        List<String> listDate = new ArrayList<String>();
        for (int i = 0; i < countDay; i++) {
            listDate.add(DateTimeUtils.getReqDateTight(DateTimeUtils.getDate(-i)));
        }
        return listDate;
    }

    public static boolean isSameDayWithToday(Date date) {
        if (date == null) {
            return false;
        }
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);
        int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
        int subMouth = todayCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH);
        int subDay = todayCal.get(Calendar.DAY_OF_MONTH) - dateCal.get(Calendar.DAY_OF_MONTH);
        return subYear == 0 && subMouth == 0 && subDay == 0;
    }
}
