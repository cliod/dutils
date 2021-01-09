package com.wobangkj.domain;

import com.wobangkj.utils.HumpUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Field;

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
		Field[] fields = type.getDeclaredFields();
		String[] columns = new String[fields.length];
		int i = 0;
		for (Field field : fields) {
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				if (StringUtils.isNotEmpty(column.name())) {
					columns[i] = column.name();
				} else {
					columns[i] = HumpUtils.humpToLine(field.getName());
				}
			} else {
				columns[i] = HumpUtils.humpToLine(field.getName());
			}
			i++;
		}
		return new Columns(columns);
	}
}
