package com.wobangkj.enums;

import com.wobangkj.api.EnumType;
import lombok.Getter;

/**
 * 关联类型
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-21 14:21:33
 */
@Getter
public enum JoinType implements EnumType {
	INNER,
	LEFT,
	RIGHT,
	;

	public static JoinType get(String name) {
		return Enum.valueOf(JoinType.class, name.toUpperCase());
	}

	/**
	 * 获取code
	 *
	 * @return code
	 */
	@Override
	public Integer getCode() {
		return this.ordinal();
	}

	/**
	 * 获取描述
	 *
	 * @return 描述
	 */
	@Override
	public String getDesc() {
		return this.name();
	}

	/**
	 * 根据数值获取对象
	 *
	 * @param code 数值
	 * @return 对象
	 */
	@Override
	public JoinType get(Integer code) {
		for (JoinType value : values()) {
			if (value.ordinal() == code) {
				return value;
			}
		}
		return null;
	}
}
