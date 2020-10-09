package com.wobangkj.utils;

import lombok.SneakyThrows;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.jetbrains.annotations.NotNull;

/**
 * 中文首字母排序
 *
 * @author cliod
 * @since 2019/7/27
 * package : com.wobangkj.git.magicked.util
 */
public class PinyinUtils {
	private static final StringBuilder pinyinStr;
	private static final HanyuPinyinOutputFormat defaultFormat;

	static {
		pinyinStr = new StringBuilder();
		defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	public PinyinUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取字符串拼音的第一个字母
	 *
	 * @param chinese 中文字符
	 * @return 拼音首字母
	 */
	@NotNull
	public static String getFirstChar(String chinese) {
		return String.valueOf(toPinyin(chinese).charAt(0));
	}

	/**
	 * 汉字转为拼音
	 *
	 * @param chinese 中文字符
	 * @return 拼音
	 */
	@SneakyThrows(value = BadHanyuPinyinOutputFormatCombination.class)
	@NotNull
	public static String toPinyin(@NotNull String chinese) {
		char[] newChar = chinese.toCharArray();
		for (char c : newChar) {
			// 小于128为英文字符
			if (c > 128) {
				pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
			} else {
				pinyinStr.append(c);
			}
		}
		return pinyinStr.toString();
	}

	/**
	 * 汉字转为拼音并获取大写的首字母;
	 *
	 * @param chinese 中文字符
	 * @return 拼音
	 */
	@NotNull
	public static String getFirstCharUpper(@NotNull String chinese) {
		return getFirstChar(chinese).toUpperCase();
	}

	/**
	 * 汉字转为拼音并获取大写的首字母;
	 *
	 * @param chinese 中文字符
	 * @return 拼音
	 */
	@NotNull
	public static String getFirstCharLower(@NotNull String chinese) {
		return getFirstChar(chinese).toLowerCase();
	}
}