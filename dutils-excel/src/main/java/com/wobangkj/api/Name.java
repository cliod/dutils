package com.wobangkj.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel file name
 *
 * @author @cliod
 * @since 4/29/20 10:43 AM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
	/**
	 * excel文件名字
	 *
	 * @return 名字
	 */
	String value() default "";
}
