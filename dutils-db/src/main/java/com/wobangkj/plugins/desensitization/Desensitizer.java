package com.wobangkj.plugins.desensitization;

import java.util.function.Function;

/**
 * 脱敏器
 *
 * @author cliod
 * @since 9/17/20 9:44 AM
 */
public interface Desensitizer extends Function<String, String> {
	/**
	 * Applies this function to the given argument.
	 *
	 * @param src the function argument
	 * @return the function result
	 */
	@Override
	default String apply(String src) {
		return this.desensitize(src);
	}

	/**
	 * 执行脱敏
	 *
	 * @param src 数据源
	 * @return 脱敏之后数据结果
	 */
	String desensitize(String src);
}
