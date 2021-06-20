package com.wobangkj.api;

import java.util.function.Supplier;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 */
@FunctionalInterface
public interface EnumText extends EnumMsg {

	/**
	 * 获取code
	 *
	 * @return code
	 */
	@Override
	default Integer getCode() {
		return 0;
	}
}