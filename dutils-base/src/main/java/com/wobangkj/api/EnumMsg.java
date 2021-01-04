package com.wobangkj.api;

import com.wobangkj.exception.AppException;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 */
public interface EnumMsg extends SessionSerializable {

	/**
	 * 获取code
	 *
	 * @return code
	 */
	Integer getCode();

	/**
	 * 获取msg
	 *
	 * @return msg
	 */
	String getMsg();

	/**
	 * 转化为对象(默认Map)
	 *
	 * @return map
	 */
	@Override
	default Object toObject() {
		Map<String, Object> map = new HashMap<>(4);
		map.put("code", this.getCode());
		map.put("msg", this.getMsg());
		return map;
	}

	/**
	 * 转换为异常
	 *
	 * @return 可抛出的异常
	 */
	default Throwable toThrowable() {
		return new AppException(this);
	}
}
