package com.wobangkj;

import com.wobangkj.utils.PinyinUtils;

/**
 * Hello world!
 *
 * @author cliod
 */
public class App {
	public static void main(String[] args) {
		String s = "你好, 杭州, tmd";
		System.out.println(PinyinUtils.toPinyin(s, true));
		for (String s1 : PinyinUtils.toPinyinArray(s, false)) {
			System.out.print(s1);
		}
	}
}
