package com.wobangkj.api;

import com.wobangkj.utils.JsonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
	@Deprecated
	default Object toObject() {
		return this;
	}

	/**
	 * 转为Map(映射)对象
	 *
	 * @return 映射
	 */
	@Override
	default Map<String, Object> toMap() {
		return new HashMap<>();
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
