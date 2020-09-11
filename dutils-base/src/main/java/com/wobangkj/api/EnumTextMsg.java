package com.wobangkj.api;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 * package : com.wobangkj.api
 */
public interface EnumTextMsg extends EnumMsg {

	/**
	 * 获取code
	 *
	 * @return code
	 */
	default Integer getCode() {
		return 271;
	}
}
