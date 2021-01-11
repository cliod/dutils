package com.wobangkj.domain;

/**
 * 区间查询
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-11 15:29:24
 */
public abstract class Among<T extends Number> extends com.wobangkj.bean.Among<T> {
	protected String column;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
}
