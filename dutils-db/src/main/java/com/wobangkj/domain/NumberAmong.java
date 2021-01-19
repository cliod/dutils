package com.wobangkj.domain;

/**
 * 一维收缩
 *
 * @author cliod
 * @see Number 属于(二维)数字
 * @since 2020-04-07
 */
public class NumberAmong extends Among<Number> {

	@Deprecated
	public static NumberAmong of(Number floor, Number ceiling) {
		return of("", floor, ceiling);
	}

	public static NumberAmong of(String column, Number floor, Number ceiling) {
		NumberAmong numberNumber = new NumberAmong();
		numberNumber.column = column;
		numberNumber.floor = floor;
		numberNumber.ceiling = ceiling;
		return numberNumber;
	}

}
