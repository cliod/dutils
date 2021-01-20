package com.wobangkj.api;

import com.wobangkj.bean.Res;

import java.util.Map;

/**
 * 转换
 *
 * @author cliod
 * @since 11/30/20 10:26 AM
 */
@FunctionalInterface
public interface IRes extends SessionSerializable {

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
	default Res toMap() {
		return this.toRes();
	}

	/**
	 * 反序列化
	 *
	 * @return 结果对象
	 */
	@Override
	@Deprecated
	default Object toObject() {
		return this.toRes();
	}
}
