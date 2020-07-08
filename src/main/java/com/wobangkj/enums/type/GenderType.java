package com.wobangkj.enums.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wobangkj.utils.JsonUtils;
import com.wobangkj.api.EnumType;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

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
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(0, "未知"),
    ;

    private final Integer code;
    private final String desc;

    GenderType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
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
            if (value.code.equals(code)) {
                return value;
            }
        }
        return this;
    }

    @Override
    public GenderType get(String name) {
        for (GenderType value : values()) {
            if (value.name().toLowerCase().equals(name.toLowerCase())) {
                return value;
            }
            if (value.desc.equals(name)) {
                return value;
            }
        }
        return this;
    }
}
