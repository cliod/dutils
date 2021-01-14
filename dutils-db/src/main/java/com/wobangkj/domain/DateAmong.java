package com.wobangkj.domain;

import java.util.Date;
import java.util.Objects;

/**
 * 一维收缩
 *
 * @author cliod
 * @see Number 属于(二维)数字
 * @since 2020-04-07
 */
public class DateAmong extends Among<Long> {

	public static DateAmong of(String column, Date start, Date end) {
		DateAmong dateAmong = new DateAmong();
		dateAmong.column = column;
		if (Objects.nonNull(start)) {
			dateAmong.floor = start.getTime();
		}
		if (Objects.nonNull(end)) {
			dateAmong.ceiling = end.getTime();
		}
		return dateAmong;
	}

	public Date getDateFloor() {
		return new Date(super.getFloor());
	}

	public void setDateFloor(Date start) {
		if (Objects.nonNull(start)) {
			this.floor = start.getTime();
		} else {
			this.floor = null;
		}
	}

	public Date getDateCeiling() {
		return new Date(super.getCeiling());
	}

	public void setDateCeiling(Date end) {
		if (Objects.nonNull(end)) {
			this.ceiling = end.getTime();
		} else {
			this.ceiling = null;
		}
	}
}
