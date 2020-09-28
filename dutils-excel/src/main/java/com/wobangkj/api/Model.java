package com.wobangkj.api;

/**
 * 读取excel通用对象
 *
 * @author @cliod
 * @since 5/6/20 2:28 PM
 * package: com.wobangkj.jzlw.utils.excel
 */
public interface Model {
	/**
	 * 新建对象
	 *
	 * @return 对象
	 */
	@Deprecated
	default Model newObj() {
		return this;
	}

	/**
	 * 新建对象
	 *
	 * @return 对象
	 */
	default Model newInstance() {
		return this;
	}

	/**
	 * 转换
	 *
	 * @return 对象实例
	 */
	Object to();
}
