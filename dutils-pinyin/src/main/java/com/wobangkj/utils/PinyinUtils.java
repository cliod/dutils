package com.wobangkj.utils;

import lombok.SneakyThrows;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * 中文首字母排序
 *
 * @author cliod
 * @since 2019/7/27
 */
public class PinyinUtils {
	private static final HanyuPinyinOutputFormat DEFAULT_FORMAT;

	static {
		DEFAULT_FORMAT = new HanyuPinyinOutputFormat();
		DEFAULT_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
		DEFAULT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	public PinyinUtils() {
	}

	public static HanyuPinyinOutputFormat getFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		return format;
	}

	/**
	 * 获取字符串拼音的第一个字母
	 *
	 * @param chinese 中文字符
	 * @return 拼音首字母
	 */
	@Deprecated
	public static @NotNull String getFirstChar(String chinese) {
		return String.valueOf(toPinyin(chinese).charAt(0));
	}

	/**
	 * 汉字转为拼音并获取大写的首字母;
	 *
	 * @param chinese 中文字符
	 * @return 拼音
	 */
	@Deprecated
	public static @NotNull String getFirstCharUpper(@NotNull String chinese) {
		return getFirstChar(chinese).toUpperCase();
	}

	/**
	 * 汉字转为拼音并获取大写的首字母;
	 *
	 * @param chinese 中文字符
	 * @return 拼音
	 */
	@Deprecated
	public static @NotNull String getFirstCharLower(@NotNull String chinese) {
		return getFirstChar(chinese).toLowerCase();
	}

	public static @NotNull String[] toPinyinArray(@NotNull String chinese) {
		return toPinyinArray(chinese, DEFAULT_FORMAT);
	}

	public static @NotNull String[] toPinyinArray(@NotNull String chinese, boolean retain) {
		return toPinyinArray(chinese, DEFAULT_FORMAT, retain);
	}

	/**
	 * 汉字转拼音, 符号会过滤
	 *
	 * @param chinese 中文汉字
	 * @param format  输出音符格式
	 * @return 数组
	 */
	public static @NotNull String[] toPinyinArray(@NotNull String chinese, HanyuPinyinOutputFormat format) {
		return toPinyinArray(chinese, format, false);
	}

	/**
	 * 汉字转拼音, 符号会过滤
	 *
	 * @param chinese 中文汉字
	 * @param format  输出音符格式
	 * @param retain  是否保留无法转换的字符
	 * @return 数组
	 */
	@SneakyThrows
	public static @NotNull String[] toPinyinArray(@NotNull String chinese, HanyuPinyinOutputFormat format, boolean retain) {
		char[] arr = chinese.toCharArray();
		// 转成句子
		String[] sentence = new String[chinese.length()];
		String[] tmp;
		char c;
		String a = "";
		int i;
		for (i = 0; i < arr.length; i++) {
			c = arr[i];
			if (c > 128) {
				if (!"".equals(a)) {
					sentence[i - 1] = a;
					a = "";
				}
				tmp = PinyinHelper.toHanyuPinyinStringArray(c, format);
				if (tmp.length > 0) {
					sentence[i] = tmp[0];
				} else {
					sentence[i] = String.valueOf(c);
				}
			} else {
				if (retain) {
					a += c;
				} else {
					if (checkLegal(c)) {
						a += c;
					}
				}
			}
		}
		if (!"".equals(a)) {
			sentence[i - 1] = a;
		}
		return Arrays.stream(sentence).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new);
	}

	@SneakyThrows
	public static @NotNull String[] toPinyinArray(@NotNull String chinese, HanyuPinyinToneType toneType) {
		HanyuPinyinOutputFormat format = getFormat();
		format.setToneType(toneType);
		return toPinyinArray(chinese, format);
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese 中文字符
	 * @return 拼音
	 */
	public static @NotNull String toPinyin(@NotNull String chinese) {
		return toPinyin(chinese, "");
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese 中文字符
	 * @return 拼音
	 */
	public static @NotNull String toPinyin(@NotNull String chinese, boolean retain) {
		return toPinyin(chinese, "", retain);
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese  中文字符
	 * @param separate 间隔符
	 * @return 拼音
	 */
	public static @NotNull String toPinyin(@NotNull String chinese, String separate) {
		return toPinyin(chinese, DEFAULT_FORMAT, separate, true);
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese  中文字符
	 * @param separate 间隔符
	 * @param retain   是否保留无法转换的字符
	 * @return 拼音
	 */
	public static @NotNull String toPinyin(@NotNull String chinese, String separate, boolean retain) {
		return toPinyin(chinese, DEFAULT_FORMAT, separate, retain);
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese  中文字符
	 * @param separate 间隔符
	 * @return 拼音
	 */
	public static @NotNull String toPinyin(@NotNull String chinese, String separate, HanyuPinyinToneType toneType) {
		HanyuPinyinOutputFormat format = getFormat();
		format.setToneType(toneType);
		return toPinyin(chinese, format, separate, true);
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese  中文字符
	 * @param separate 间隔符
	 * @param retain   是否保留无法转换的字符
	 * @return 拼音
	 */
	public static @NotNull String toPinyin(@NotNull String chinese, String separate, HanyuPinyinToneType toneType, boolean retain) {
		HanyuPinyinOutputFormat format = getFormat();
		format.setToneType(toneType);
		return toPinyin(chinese, format, separate, retain);
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese  中文字符
	 * @param separate 间隔符
	 * @return 拼音
	 */
	public static @NotNull String toPinyin(@NotNull String chinese, HanyuPinyinOutputFormat format, String separate) {
		return toPinyin(chinese, format, separate, true);
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese  中文字符
	 * @param separate 间隔符
	 * @param format   拼音输出格式
	 * @param retain   是否保留无法转换的字符
	 * @return 拼音
	 */
	@SneakyThrows
	public static @NotNull String toPinyin(@NotNull String chinese, HanyuPinyinOutputFormat format, String separate, boolean retain) {
		return PinyinHelper.toHanYuPinyinString(chinese, format, separate, retain);
	}

	/**
	 * 检查字符是否合法
	 *
	 * @return 是否合法
	 */
	public static boolean checkLegal(char c) {
		int a = '0';
		int b = '9';
		int d = 'a';
		int e = 'z';
		int f = 'A';
		int g = 'Z';
		return (a <= (int) c && (int) c <= b) || (d <= (int) c && (int) c <= e) || (f <= (int) c && (int) c <= g);
	}
}