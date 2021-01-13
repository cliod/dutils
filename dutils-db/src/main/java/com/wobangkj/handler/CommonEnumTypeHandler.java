package com.wobangkj.handler;

import com.wobangkj.api.EnumType;
import lombok.extern.slf4j.Slf4j;

/**
 * Mybatis 通用枚举类型转换
 *
 * @author cliod
 * @since 2020-06-21
 */
@Slf4j
public class CommonEnumTypeHandler extends BaseEnumTypeHandler<EnumType> {
	@Override
	protected EnumType get(Integer v) {
		return null;
	}
}
