package com.wobangkj.utils;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * BigDecimal运算工具类
 *
 * @author cliod
 * @since 10/15/20 11:02 AM
 */
public class DecimalUtils {

	/**
	 * 加法计算（result = x + y）
	 *
	 * @param x 被加数（可为null）
	 * @param y 加数 （可为null）
	 * @return 和 （可为null）
	 */
	public static @Nullable BigDecimal add(@Nullable BigDecimal x, @Nullable BigDecimal y) {
		if (x == null) {
			return y;
		}
		if (y == null) {
			return x;
		}
		return x.add(y);
	}

	/**
	 * 加法计算（result = a + b + c）
	 *
	 * @param a 被加数（可为null）
	 * @param b 加数（可为null）
	 * @param c 加数（可为null）
	 * @return BigDecimal （可为null）
	 */
	public static @Nullable BigDecimal add(@Nullable BigDecimal a, @Nullable BigDecimal b, @Nullable BigDecimal c) {
		return add(add(a, b), c);
	}

	/**
	 * 加法计算（result = a + b + c + d）
	 *
	 * @param a 被加数（可为null）
	 * @param b 加数（可为null）
	 * @param c 加数（可为null）
	 * @param d 加数（可为null）
	 * @return BigDecimal （可为null）
	 */
	public static @Nullable BigDecimal add(@Nullable BigDecimal a, @Nullable BigDecimal b, @Nullable BigDecimal c, @Nullable BigDecimal d) {
		return add(add(a, b), add(c, d));
	}

	/**
	 * 加法计算（result = a + b + c + d + ...）
	 *
	 * @param a 被加数（可为null）,加数（可为null）
	 * @return BigDecimal （可为null）
	 */
	public static @Nullable BigDecimal add(@Nullable BigDecimal... a) {
		if (Objects.isNull(a)) {
			return null;
		}
		if (a.length == 1) {
			return a[0];
		}
		BigDecimal a0 = a[0];
		for (BigDecimal decimal : a) {
			a0 = add(a0, decimal);
		}
		return subtract(a0, a[0]);
	}

	/**
	 * 累加计算(result=x + result)
	 *
	 * @param x      被加数（可为null）
	 * @param result 和 （可为null,若被加数不为为null，result默认值为0）
	 * @return result 和 （可为null）
	 */
	public static BigDecimal accumulate(BigDecimal x, BigDecimal result) {
		if (x == null) {
			return result;
		}
		if (result == null) {
			result = new BigDecimal("0");
		}
		return result.add(x);
	}

	/**
	 * 减法计算(result = x - y)
	 *
	 * @param x 被减数（可为null）
	 * @param y 减数（可为null）
	 * @return BigDecimal 差 （可为null）
	 */
	public static BigDecimal subtract(BigDecimal x, BigDecimal y) {
		if (x == null || y == null) {
			return null;
		}
		return x.subtract(y);
	}

	/**
	 * 乘法计算(result = x × y)
	 *
	 * @param x 乘数(可为null)
	 * @param y 乘数(可为null)
	 * @return BigDecimal 积
	 */
	public static BigDecimal multiply(BigDecimal x, BigDecimal y) {
		if (x == null || y == null) {
			return null;
		}
		return x.multiply(y);
	}

	/**
	 * 除法计算(result = x ÷ y)
	 *
	 * @param x 被除数（可为null）
	 * @param y 除数（可为null）
	 * @return 商 （可为null,四舍五入，默认保留20位小数）
	 */
	public static BigDecimal divide(BigDecimal x, BigDecimal y) {
		if (x == null || y == null || y.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		// 结果为0.000..时，不用科学计数法展示
		return stripTrailingZeros(x.divide(y, 20, BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 转为字符串(防止返回可续计数法表达式)
	 *
	 * @param x 要转字符串的小数
	 * @return String
	 */
	public static String toPlainString(BigDecimal x) {
		if (x == null) {
			return null;
		}
		return x.toPlainString();
	}

	/**
	 * 保留小数位数
	 *
	 * @param x     目标小数
	 * @param scale 要保留小数位数
	 * @return BigDecimal 结果四舍五入
	 */
	public static BigDecimal scale(BigDecimal x, int scale) {
		if (x == null) {
			return null;
		}
		return x.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 整型转为BigDecimal
	 *
	 * @param x (可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal toBigDecimal(Integer x) {
		if (x == null) {
			return null;
		}
		return new BigDecimal(x.toString());
	}

	/**
	 * 长整型转为BigDecimal
	 *
	 * @param x (可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal toBigDecimal(Long x) {
		if (x == null) {
			return null;
		}
		return new BigDecimal(x.toString());
	}

	/**
	 * 双精度型转为BigDecimal
	 *
	 * @param x (可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal toBigDecimal(Double x) {
		if (x == null) {
			return null;
		}
		return new BigDecimal(x.toString());
	}

	/**
	 * 单精度型转为BigDecimal
	 *
	 * @param x (可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal toBigDecimal(Float x) {
		if (x == null) {
			return null;
		}
		return new BigDecimal(x.toString());
	}

	/**
	 * 字符串型转为BigDecimal
	 *
	 * @param x (可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal toBigDecimal(String x) {
		if (x == null) {
			return null;
		}
		return new BigDecimal(x);
	}

	/**
	 * 对象类型转为BigDecimal
	 *
	 * @param x (可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal toBigDecimal(Object x) {
		if (x == null) {
			return null;
		}
		BigDecimal result = null;
		try {
			result = new BigDecimal(x.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 倍数计算，用于单位换算
	 *
	 * @param x        目标数(可为null)
	 * @param multiple 倍数 (可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal multiple(BigDecimal x, Integer multiple) {
		if (x == null || multiple == null) {
			return null;
		}
		return DecimalUtils.multiply(x, toBigDecimal(multiple));
	}

	/**
	 * 去除小数点后的0（如: 输入1.000返回1）
	 *
	 * @param x 目标数(可为null)
	 * @return BigDecimal (可为null)
	 */
	public static BigDecimal stripTrailingZeros(BigDecimal x) {
		if (x == null) {
			return null;
		}
		return x.stripTrailingZeros();
	}
}
