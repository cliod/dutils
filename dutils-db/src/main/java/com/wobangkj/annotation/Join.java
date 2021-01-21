package com.wobangkj.annotation;

import com.wobangkj.enums.JoinType;

import java.lang.annotation.*;

/**
 * 数据库表关联
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-21 14:20:55
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Join {
	/**
	 * 关联类型
	 *
	 * @return 关联类型
	 */
	JoinType joinType();

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
	 * 目标对象
	 *
	 * @return 实体类
	 */
	Class<?> targetEntity() default void.class;

	/**
	 * 用于查询的字段
	 *
	 * @return 字段
	 */
	String mappedBy() default "";
}
