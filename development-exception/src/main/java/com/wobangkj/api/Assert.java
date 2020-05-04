package com.wobangkj.api;

import com.wobangkj.exception.AppException;

/**
 * assert
 *
 * @author @cliod
 * @since 4/28/20 11:53 AM
 * package: com.wobangkj.api
 */
public interface Assert {
    /**
     * 创建异常
     *
     * @param args 参数
     * @return 结果
     */
    AppException newException(Object... args);

    /**
     * 创建异常
     *
     * @param t    可抛出的异常
     * @param args 参数
     * @return 结果
     */
    AppException newException(Throwable t, Object... args);

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException(obj);
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param obj  待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(new RuntimeException(), args);
        }
    }
}
