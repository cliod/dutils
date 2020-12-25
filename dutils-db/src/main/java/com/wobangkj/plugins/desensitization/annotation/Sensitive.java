package com.wobangkj.plugins.desensitization.annotation;

import com.wobangkj.plugins.desensitization.DesensitizeStrategy;
import com.wobangkj.plugins.desensitization.enums.BaseDesensitizeStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感标记, 用于标注脱敏对象
 *
 * @author cliod
 * @since 9/17/20 10:18 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Sensitive {
	/**
	 * 特殊脱敏
	 */
	Class<? extends DesensitizeStrategy> special() default BaseDesensitizeStrategy.class;
	/**
	 * 脱敏策略，当特殊脱敏存在不为null也不是BaseDesensitizeStrategy时，该字段失效，默认无策略
	 */
	BaseDesensitizeStrategy strategy() default BaseDesensitizeStrategy.NUNO;
}
