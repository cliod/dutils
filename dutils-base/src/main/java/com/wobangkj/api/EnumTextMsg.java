package com.wobangkj.api;

import java.util.function.Supplier;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 */
@FunctionalInterface
public interface EnumTextMsg extends EnumMsg, Supplier<String> {

	/**
	 * 获取code
	 *
	 * @return code
	 */
	@Override
	default Integer getCode() {
		return 271;
	}

	/**
	 * 错误message
	 *
	 * @param msg  消息格式
	 * @param args 参数
	 * @return 消息
	 */
	default String getMsg(String msg, Object... args) {
		return getMsg() + " " + String.format(msg, args);
	}

	/**
	 * 错误message
	 *
	 * @param args 参数
	 * @return 结果
	 */
	default String getMsg(Object... args) {
		return String.format(getMsg(), args);
	}

	/**
	 * Gets a result.
	 *
	 * @return a result
	 */
	@Override
	default String get() {
		return getMsg();
	}
}
