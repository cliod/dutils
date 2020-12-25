package com.wobangkj.api.asserts;

import com.wobangkj.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * assert interface
 *
 * @author @cliod
 * @since 4/28/20 11:53 AM
 */
public interface IAssert {

	/**
	 * 获取code
	 *
	 * @return code
	 */
	int getStatus();

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
	 * @param obj 待判断对象
	 * @param msg 消息
	 */
	default void notNull(Object obj, String msg) {
		if (obj == null) {
			throw newException(getStatus(), msg);
		}
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj             待判断对象
	 * @param messageSupplier 消息
	 */
	default void notNull(Object obj, Supplier<String> messageSupplier) {
		notNull(obj, nullSafeGet(messageSupplier));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
	 *
	 * @param obj  待判断对象
	 * @param msg  消息
	 * @param args message占位符对应的参数列表
	 */
	default void notNull(Object obj, String msg, Object... args) {
		notNull(obj, String.format(msg, args));
	}

	/**
	 * 断言是否为真
	 *
	 * @param expression 真表达式
	 * @param message    消息
	 */
	default void isTrue(boolean expression, String message) {
		if (!expression) {
			throw newException(getStatus(), message);
		}
	}

	/**
	 * 断言是否为真
	 *
	 * @param expression 真表达式
	 * @param message    消息
	 * @param args       message占位符对应的参数列表
	 */
	default void isTrue(boolean expression, String message, Object... args) {
		isTrue(expression, String.format(message, args));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param expression      待判断对象
	 * @param messageSupplier 消息
	 */
	default void isTrue(boolean expression, Supplier<String> messageSupplier) {
		isTrue(expression, nullSafeGet(messageSupplier));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj 待判断对象
	 * @param msg 消息
	 */
	default void notEmpty(CharSequence obj, String msg) {
		if (StringUtils.isBlank(obj)) {
			throw newException(getStatus(), msg);
		}
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj             待判断对象
	 * @param messageSupplier 消息
	 */
	default void notEmpty(CharSequence obj, Supplier<String> messageSupplier) {
		notNull(obj, nullSafeGet(messageSupplier));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
	 *
	 * @param obj  待判断对象
	 * @param msg  消息
	 * @param args message占位符对应的参数列表
	 */
	default void notEmpty(CharSequence obj, String msg, Object... args) {
		notEmpty(obj, String.format(msg, args));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj 待判断对象
	 * @param msg 消息
	 */
	default void notEmpty(Collection<?> obj, String msg) {
		if (CollectionUtils.isEmpty(obj)) {
			throw newException(getStatus(), msg);
		}
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj             待判断对象
	 * @param messageSupplier 消息
	 */
	default void notEmpty(Collection<?> obj, Supplier<String> messageSupplier) {
		notNull(obj, nullSafeGet(messageSupplier));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
	 *
	 * @param obj  待判断对象
	 * @param msg  消息
	 * @param args message占位符对应的参数列表
	 */
	default void notEmpty(Collection<?> obj, String msg, Object... args) {
		notEmpty(obj, String.format(msg, args));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj 待判断对象
	 * @param msg 消息
	 */
	default void notEmpty(Map<?, ?> obj, String msg) {
		if (Objects.isNull(obj) || obj.isEmpty()) {
			throw newException(getStatus(), msg);
		}
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 *
	 * @param obj             待判断对象
	 * @param messageSupplier 消息
	 */
	default void notEmpty(Map<?, ?> obj, Supplier<String> messageSupplier) {
		notNull(obj, nullSafeGet(messageSupplier));
	}

	/**
	 * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
	 * <p>异常信息<code>message</code>支持传递参数方式，避免在判断之前进行字符串拼接操作
	 *
	 * @param obj  待判断对象
	 * @param msg  消息
	 * @param args message占位符对应的参数列表
	 */
	default void notEmpty(Map<?, ?> obj, String msg, Object... args) {
		notEmpty(obj, String.format(msg, args));
	}

	/**
	 * 安全获取消息
	 *
	 * @param messageSupplier 消息获取器
	 * @return 消息
	 */
	default String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
		return (messageSupplier != null ? messageSupplier.get() : null);
	}
}
