package com.wobangkj.utils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 匹配工具
 *
 * @author cliod
 * @since 11/14/20 9:47 AM
 */
public class MatchUtils {

	public static final Pattern FLOAT_PATTERN = Pattern.compile("^[-\\+]?([1-9]\\d*.\\d*|0\\.\\d*[1-9]\\d*)");
	public static final Pattern INT_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");
	/**
	 * 验证手机号
	 */
	public static final Pattern MOBILE_PATTERN = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
	public static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
	public static final Pattern DATE_PATTERN = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-/\\s]?((((0?[13578])|(1[02]))" +
			"[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-/\\s]?((((0?[13578])|(1[02]))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3])):([0-5]?[0-9])((\\s)|(:([0-5]?[0-9])))))?$");

	public static boolean isFloat(String str) {
		if (Objects.isNull(str) || str.isEmpty()) {
			return false;
		}
		return FLOAT_PATTERN.matcher(str).matches();
	}

	public static boolean isInteger(String str) {
		if (Objects.isNull(str) || str.isEmpty()) {
			return false;
		}
		return INT_PATTERN.matcher(str).matches();
	}

	/**
	 * 判断是否为数值
	 *
	 * @param str 字符串
	 * @return 结果
	 */
	public static boolean isNumber(String str) {
		return isFloat(str) | isInteger(str);
	}

	/**
	 * 手机号验证
	 *
	 * @param str 字符串
	 * @return 验证通过返回true
	 * @author ：shijing
	 * 2016年12月5日下午4:34:46
	 */
	public static boolean isMobile(final String str) {
		return MOBILE_PATTERN.matcher(str).matches();
	}

	/**
	 * 功能：判断字符串是否为数字
	 *
	 * @param str 字符串
	 * @return 结果
	 */
	public static boolean isNumeric(String str) {
		return NUMBER_PATTERN.matcher(str).matches();
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 *
	 * @param strDate 时间
	 * @return 结果
	 */
	public static boolean isDate(String strDate) {
		return DATE_PATTERN.matcher(strDate).matches();
	}
}
