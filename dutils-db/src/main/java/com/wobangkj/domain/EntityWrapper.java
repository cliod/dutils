package com.wobangkj.domain;

import com.wobangkj.annotation.ColumnExt;
import com.wobangkj.annotation.Join;
import com.wobangkj.annotation.LikeColumn;
import com.wobangkj.annotation.LikeExclude;
import lombok.Getter;

import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 实体类封装
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 11:35:23
 */
public class EntityWrapper<T> {
	/**
	 * 实体类型
	 */
	@Getter
	private Class<T> entityType;
	/**
	 * 实体字段
	 */
	@Getter
	private String[] columns;
	/**
	 * 实体额外字段
	 */
	@Getter
	private Map<String, Annotation> columnsExt;

	protected EntityWrapper() {
	}

	public EntityWrapper(Class<T> entityType) {
		this.parseField(entityType);
	}

	/**
	 * 根据Entity类型进行包装
	 *
	 * @param type 类型
	 * @param <T>  泛型
	 * @return 结果
	 */
	public static <T> EntityWrapper<T> wrapper(Class<T> type) {
		EntityWrapper<T> wrapper = new EntityWrapper<>();
		wrapper.parseField(type);
		return wrapper;
	}

	/**
	 * 从包装器转化而来
	 *
	 * @param entityWrapper 包装器
	 * @param <T>           泛型
	 * @return 结果
	 */
	public static <T> EntityWrapper<T> wrapper(EntityWrapper<?> entityWrapper) {
		EntityWrapper<T> wrapper = new EntityWrapper<>();
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>) entityWrapper.entityType;
		wrapper.entityType = type;
		wrapper.columns = entityWrapper.columns;
		wrapper.columnsExt = entityWrapper.columnsExt;
		return wrapper;
	}

	/**
	 * 解析用于like模糊查询的字段
	 *
	 * @param type 类型
	 * @return 字段
	 */
	protected void parseField(Class<T> type) {
		this.entityType = type;
		Field[] fields = this.entityType.getDeclaredFields();
		List<String> columns = new ArrayList<>(fields.length);
		LikeColumn like = this.entityType.getAnnotation(LikeColumn.class);
		List<String> exclude = new ArrayList<>();
		if (Objects.nonNull(like)) {
			if (like.includeOnly().length != 0) {
				this.columns = like.includeOnly();
				return;
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
			if (field.isAnnotationPresent(ColumnExt.class)) {
				if (Objects.isNull(this.columnsExt)) {
					this.columnsExt = new HashMap<>();
				}
				this.columnsExt.put(fieldName, field.getAnnotation(ColumnExt.class));
				continue;
			} else if (field.isAnnotationPresent(Join.class)) {
				if (Objects.isNull(this.columnsExt)) {
					this.columnsExt = new HashMap<>();
				}
				this.columnsExt.put(fieldName, field.getAnnotation(Join.class));
				continue;
			}
			columns.add(fieldName);
		}
		this.columns = columns.toArray(new String[0]);
	}

	public void setEntityType(Class<T> entityType) {
		this.parseField(entityType);
	}

	protected void setColumns(String[] columns) {
		this.columns = columns;
	}

	protected void setColumnsExt(Map<String, Annotation> columnsExt) {
		this.columnsExt = columnsExt;
	}
}
