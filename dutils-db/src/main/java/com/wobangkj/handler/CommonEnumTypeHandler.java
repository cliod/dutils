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
		return Inner.NULL;
	}

	private enum Inner implements EnumType {
		NULL;

		@Override
		public Integer getCode() {
			return null;
		}

		@Override
		public String getDesc() {
			return null;
		}

		@Override
		public EnumType get(Integer code) {
			return null;
		}
	}
}
