package com.wobangkj.domain;

import com.wobangkj.annotation.LikeColumn;
import com.wobangkj.annotation.LikeExclude;
import lombok.Getter;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
		String[] fields = parseField(type).toArray(new String[0]);
		return new Columns(fields);
	}

	/**
	 * 解析用于like模糊查询的字段
	 *
	 * @param type 类型
	 * @return 字段
	 */
	public static List<String> parseField(Class<?> type) {
		Field[] fields = type.getDeclaredFields();
		List<String> columns = new ArrayList<>(fields.length);
		LikeColumn like = type.getAnnotation(LikeColumn.class);
		List<String> exclude = new ArrayList<>();
		if (Objects.nonNull(like)) {
			if (like.includeOnly().length != 0) {
				columns = Arrays.asList(like.includeOnly());
				return columns;
			}
			exclude.addAll(Arrays.asList(like.exclude()));
		}
		String fieldName;
		for (Field field : fields) {
			// 不获取静态和最终字段
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			fieldName = field.getName();
			// 忽略like
			if (exclude.contains(fieldName)) {
				continue;
			}
			// 忽略like
			if (field.isAnnotationPresent(LikeExclude.class) || field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			columns.add(fieldName);
		}
		return columns;
	}

	protected void setColumns(String[] columns) {
		this.columns = columns;
	}
}
