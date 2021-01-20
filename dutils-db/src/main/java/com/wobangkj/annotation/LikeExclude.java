package com.wobangkj.annotation;

import java.lang.annotation.*;

/**
 * 不参与like查询
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-20 10:04:12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LikeExclude {
}
