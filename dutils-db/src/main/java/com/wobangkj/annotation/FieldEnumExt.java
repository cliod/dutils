package com.wobangkj.annotation;

import com.wobangkj.api.EnumType;

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
public @interface FieldEnumExt {

	Class<? extends EnumType> ext();
}
