package com.wobangkj.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 一维收缩
 *
 * @author cliod
 * @see Number 属于(二维)数字
 * @since 2020-04-07
 */
public class NumberAmong extends Among<Number> {

	public static NumberAmong of(Number start, Number end) {
		NumberAmong dateAmong = new NumberAmong();
		dateAmong.floor = start;
		dateAmong.ceiling = end;
		return dateAmong;
	}
}
