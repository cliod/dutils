package com.wobangkj.enums.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wobangkj.api.EnumType;
import com.wobangkj.utils.JsonUtils;
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
        return Entry.convert(this);
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
            if (value.code.equals(code)) {
                return value;
            }
        }
        return this;
    }

    /**
     * 内置对象
     */
    @Data
    public static class Entry implements Serializable {

        private static final long serialVersionUID = 4140185593972696603L;
        /**
         * 状态码
         */
        private Integer code;
        /**
         * 消息
         */
        private String desc;

        static <T extends Enum<T>> @NotNull Entry convert(@NotNull EnumType e) {
            Entry entry = new Entry();
            entry.setCode(e.getCode());
            entry.setDesc(e.getDesc());
            return entry;
        }

        @Override
        public String toString() {
            return JsonUtils.toJson(this);
        }
    }
}
