package com.wobangkj.domain;

/**
 * 数据库字段
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 11:35:23
 */
@Deprecated
public class Columns<T> extends EntityWrapper<T> {

	public static Columns<?> of(Class<?> type) {
		Columns<?> columns = new Columns<>();
		columns.parseField((Class) type);
		return columns;
	}

	protected void setColumns(String[] columns) {
		super.setColumns(columns);
	}
}
