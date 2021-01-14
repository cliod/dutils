package com.wobangkj.domain;

import com.wobangkj.utils.RefUtils;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 数据库字段
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 11:35:23
 */
@Data
public class Columns {
	private String[] columns;

	protected Columns(String[] columns) {
		this.columns = columns;
	}

	public static Columns of(Class<?> type) {
		String[] fields = RefUtils.getFieldNames(type, Transient.class).toArray(new String[0]);
		return new Columns(fields);
	}
}
