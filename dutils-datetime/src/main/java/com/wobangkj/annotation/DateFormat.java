package com.wobangkj.annotation;

import com.wobangkj.enums.Format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时间格式
 *
 * @author cliod
 * @since 7/9/20 3:36 PM
 */
@Deprecated
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    /**
     * 样式
     *
     * @return 样式
     */
    String pattern();
    /**
     * 样式
     *
     * @return 样式
     */
    Format format() default Format.DATETIME_DEFAULT;
}
