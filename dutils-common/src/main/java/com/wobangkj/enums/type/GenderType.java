package com.wobangkj.enums.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wobangkj.api.EnumType;
import com.wobangkj.bean.Maps;
import com.wobangkj.utils.JsonUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 性别枚举
 *
 * @author cliod
 * @since 19-5-25
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderType implements EnumType {
	/**
	 * 性别
	 */
	MALE(0, "男"),
	FEMALE(1, "女"),
	UNKNOWN(2, "未知"),
	;

	private final Integer code;
	private final String desc;

	GenderType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public @NotNull Object toObject() {
		return Maps.of("code", (Object) this.getCode()).add("desc", this.getDesc());
	}

	@Override
	public @NotNull String toJson() {
		return JsonUtils.toJson(this);
	}

	@Override
	public @NotNull String toString() {
		return this.toJson();
	}

	@Override
	public GenderType get(Integer code) {
		if (code == null) {
			return this;
		}
		return this.get(code.intValue());
	}

	public GenderType get(int code) {
		for (GenderType value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return this;
	}
}
