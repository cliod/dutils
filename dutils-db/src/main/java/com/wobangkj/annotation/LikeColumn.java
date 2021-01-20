package com.wobangkj.annotation;

import java.lang.annotation.*;

/**
 * key关键字的like查询的指定字段
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-20 10:05:34
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface LikeColumn {
	/**
	 * 不参与查询的字段
	 *
	 * @return 字段
	 */
	String[] exclude() default {};

	/**
	 * 仅包含指定字段
	 * 优先级高于{@link LikeColumn#exclude()}和{@link LikeExclude}
	 *
	 * @return 字段
	 */
	String[] includeOnly() default {};
}
