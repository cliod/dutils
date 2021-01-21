package com.wobangkj.annotation;

import com.wobangkj.enums.JoinType;

import java.lang.annotation.*;

/**
 * 字段扩展，多表关联
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-21 13:34:47
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ColumnExt {
	/**
	 * 查询语句(当该字段不为空时，其他字段无效)
	 *
	 * @return sql语句
	 */
	String querySql() default "";

	/**
	 * 表名称
	 *
	 * @return 表名称
	 */
	String tableName() default "";

	/**
	 * 用于查询的字段
	 *
	 * @return 字段
	 */
	String columnName() default "";

	/**
	 * 用于查询的字段
	 *
	 * @return 字段
	 */
	String linkedName() default "";
}
