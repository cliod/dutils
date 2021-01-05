package com.wobangkj.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 1/5/21 11:47 AM
 */
public class PinyinUtilsTest {

	@Test
	public void toPinyin() {
		String s = "你好, 杭州, tmd";
		System.out.println(PinyinUtils.toPinyin(s, true));
		for (String s1 : PinyinUtils.toPinyinArray(s, false)) {
			System.out.print(s1 + " ");
		}
		System.out.println();
		System.out.println(PinyinUtils.toPinyin("你好"));
	}
}