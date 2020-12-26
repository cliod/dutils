package com.wobangkj.api;

import java.util.function.Supplier;

/**
 * 获取样式
 *
 * @author cliod
 * @since 7/8/20 5:02 PM
 */
@FunctionalInterface
public interface Format<T> extends ValueWrapper<T>, Supplier<T> {
	/**
	 * 获取样式
	 *
	 * @return 样式
	 */
	T getPattern();

	/**
	 * 获取内容
	 *
	 * @return 值
	 */
	@Override
	default T value() {
		return getPattern();
	}
}
