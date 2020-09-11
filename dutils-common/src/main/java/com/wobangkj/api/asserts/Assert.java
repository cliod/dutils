package com.wobangkj.api.asserts;

import com.wobangkj.exception.AppException;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * assert
 *
 * @author @cliod
 * @since 4/28/20 11:53 AM
 * package: com.wobangkj.api
 */
public interface Assert {

    /**
     * 创建可视异常
     *
     * @param args message占位符对应的参数列表
     * @return 结果
     */
    AppException newException(Object... args);

    /**
     * 创建其他异常
     *
     * @param t    可抛出的异常
     * @param args 参数
     * @return 结果
     */
    AppException newException(Throwable t, Object... args);

    /**
     * 创建可视异常
     *
     * @param code 参数(271)
     * @param msg  参数显示文字
     * @return 结果
     */
    default AppException newException(int code, String msg) {
        return new AppException(code, msg);
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj             待判断对象
     * @param messageSupplier 消息
     */
    default void assertNotNull(Object obj, Supplier<String> messageSupplier) {
        assertNotNull(obj, nullSafeGet(messageSupplier));
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     * @param msg 消息
     */
    default void assertNotNull(Object obj, String msg) {
        if (obj == null) {
            throw newException(271, msg);
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     * @param obj  待判断对象
     * @param msg  消息
     * @param args message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, String msg, Object... args) {
        if (obj == null) {
            throw newException(271, String.format(msg, args));
        }
    }

    default String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }
}
