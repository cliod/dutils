package com.wobangkj.api;

import com.wobangkj.utils.JsonUtils;

import java.io.Serializable;

/**
 * 会话
 *
 * @author cliod
 * @since 2019/11/9
 */
public interface SessionSerializable extends Serializer, Deserializer, Serializable {

	/**
	 * 转成对象
	 *
	 * @return obj
	 */
	@Override
	default Object toObject() {
		return this;
	}

	/**
	 * 对象转成json
	 *
	 * @return json
	 */
	@Override
	default String toJson() {
		return JsonUtils.toJson(this.toObject());
	}
}
