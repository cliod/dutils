package com.wobangkj.domain;

import com.wobangkj.annotation.Columns;
import com.wobangkj.annotation.*;
import lombok.Getter;

import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
	private Class<?> entityType;
	/**
	 * 实体字段
	 */
	@Getter
	private String[] includeColumns;
	/**
	 * 实体字段，当前字段内容 小于等于 {@link #includeColumns}
	 */
	@Getter
	private String[] likeColumns;
	/**
	 * 实体额外字段
	 */
	@Getter
	private Map<String, Annotation> columnsExt;

	protected EntityWrapper() {
		this.entityType = this.getType();
		this.parseField();
	}

	public EntityWrapper(Class<? extends T> entityType) {
		this.entityType = entityType;
		this.parseField();
	}

	/**
	 * 根据Entity类型进行包装
	 *
	 * @param type 类型
	 * @param <T>  泛型
	 * @return 结果
	 */
	public static <T> EntityWrapper<? extends T> wrapper(Class<? extends T> type) {
		return new EntityWrapper<>(type);
	}

	/**
	 * 从包装器转化而来
	 *
	 * @param entityWrapper 包装器
	 * @param <T>           泛型
	 * @return 结果
	 */
	public static <T> EntityWrapper<? extends T> wrapper(EntityWrapper<?> entityWrapper) {
		EntityWrapper<T> wrapper = new EntityWrapper<>();
		@SuppressWarnings("unchecked")
		Class<? extends T> type = (Class<? extends T>) entityWrapper.entityType;
		wrapper.entityType = type;
		wrapper.includeColumns = entityWrapper.includeColumns;
		wrapper.columnsExt = entityWrapper.columnsExt;
		return wrapper;
	}

	/**
	 * 获取实体字段
	 */
	public String[] getColumns() {
		return includeColumns;
	}

	/**
	 * 设置实体字段
	 *
	 * @param columns 字段数组
	 */
	protected void setColumns(String[] columns) {
		this.includeColumns = columns;
	}

	public void setEntityType(Class<? extends T> entityType) {
		this.entityType = entityType;
		this.parseField();
	}

	protected void setColumnsExt(Map<String, Annotation> columnsExt) {
		this.columnsExt = columnsExt;
	}

	/**
	 * 获取泛型的类型
	 *
	 * @return 类型对象
	 */
	public Class<?> getType() {
		Class<?> ew = this.getClass();
		ParameterizedType pt = (ParameterizedType) ew.getGenericSuperclass();
		Type[] ews = pt.getActualTypeArguments();
		for (Type type : ews) {
			if (type instanceof Class) {
				ew = (Class<?>) type;
				break;
			}
		}
		return ew;
	}

	/**
	 * 解析用于查询的字段
	 *
	 * @return 字段
	 */
	protected void parseField() {
		// 获取实体类型所有的属性
		Field[] fields = this.entityType.getDeclaredFields();
		// 是否存在{@link Columns}注解
		Columns annotation = this.entityType.getAnnotation(Columns.class);
		// 是否存在{@link LikeColumn}注解
		LikeColumn like = this.entityType.getAnnotation(LikeColumn.class);
		// 排除不用于查询的字段属性名称
		List<String> excludeFieldNames = new ArrayList<>();
		List<String> excludeLikeFieldNames = new ArrayList<>();
		if (Objects.nonNull(annotation)) {
			if (annotation.includeOnly().length != 0) {
				this.likeColumns = annotation.includeOnly();
			}
			excludeFieldNames.addAll(Arrays.asList(annotation.exclude()));
		}
		if (Objects.nonNull(like)) {
			if (like.includeOnly().length != 0) {
				this.likeColumns = like.includeOnly();
			}
			excludeLikeFieldNames.addAll(Arrays.asList(like.exclude()));
		}
		String fieldName; // 临时记录
		List<String> columnNames = new ArrayList<>(fields.length);
		List<String> likeNames = new ArrayList<>(fields.length);
		for (Field field : fields) {
			// 不获取静态和最终字段
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			fieldName = field.getName();
			// 忽略手动忽略的字段属性 -> 同时会忽略like查询
			if (excludeFieldNames.contains(fieldName)) {
				continue;
			}
			// 忽略{@link Transient}注解的字段
			if (field.isAnnotationPresent(Transient.class)) {
				continue;
			}
			// 是否扩展字段
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
			columnNames.add(fieldName);
			// 不被忽略
			if (!field.isAnnotationPresent(LikeExclude.class) && !excludeLikeFieldNames.contains(fieldName)) {
				likeNames.add(fieldName);
			}
		}
		this.includeColumns = columnNames.toArray(new String[0]);
		this.likeColumns = likeNames.toArray(new String[0]);
	}

	/**
	 * 解析用于查询的字段
	 *
	 * @param type 类型
	 * @return 字段
	 */
	@Deprecated
	protected void parseField(Class<? extends T> type) {
		this.entityType = type;
		this.parseField();
	}

}
