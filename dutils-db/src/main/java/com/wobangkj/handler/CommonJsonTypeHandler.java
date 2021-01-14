package com.wobangkj.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Mybatis 通用Json类型转换
 *
 * @author cliod
 * @since 2020-06-21
 */
@Slf4j
public class CommonJsonTypeHandler extends BaseJsonTypeHandler<Serializable> {
	@Override
	protected Serializable get(String json) {
		log.debug("从数据库获取到的json为 {}", json);
		return (Serializable) JSON.parse(json);
	}

	@Override
	protected Serializable get(String json, Class<Serializable> clazz) {
		log.debug("从数据库获取到的json为 {}，映射到{}类型。", json, clazz.getName());
		return super.get(json, clazz);
	}
}
