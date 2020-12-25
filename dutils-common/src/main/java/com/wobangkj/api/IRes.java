package com.wobangkj.api;

import com.wobangkj.bean.Res;

/**
 * 转换
 *
 * @author cliod
 * @since 11/30/20 10:26 AM
 */
@FunctionalInterface
public interface IRes extends com.wobangkj.api.Deserializer {

	/**
	 * 转成Res[Map]对象
	 *
	 * @return Map
	 */
	Res toRes();

	/**
	 * 反序列化
	 *
	 * @return 结果对象
	 */
	@Override
	default Object toObject() {
		return this.toRes();
	}
}
