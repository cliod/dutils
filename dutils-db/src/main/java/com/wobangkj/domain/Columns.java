package com.wobangkj.domain;

import com.wobangkj.utils.RefUtils;
import lombok.Getter;

import javax.persistence.Transient;

/**
 * 数据库字段
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 11:35:23
 */
public class Columns {
	@Getter
	private String[] columns;

	protected Columns(String[] columns) {
		this.columns = columns;
	}

	public static Columns of(Class<?> type) {
		String[] fields = RefUtils.getFieldNames(type, Transient.class).toArray(new String[0]);
		return new Columns(fields);
	}

	protected void setColumns(String[] columns) {
		this.columns = columns;
	}
}
