package com.wobangkj.api.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel file name
 *
 * @author @cliod
 * @since 4/29/20 10:43 AM
 * package: com.wobangkj.jzlw.utils.excel
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
