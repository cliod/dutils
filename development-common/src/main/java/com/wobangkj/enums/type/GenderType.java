package com.wobangkj.enums.type;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wobangkj.api.EnumType;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author cliod
 * @desc 性别枚举
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

    @NotNull
    @Override
    public Object toObject() {
        return Entry.convert(this);
    }

    @NotNull
    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }

    @NotNull
    @Override
    public String toString() {
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

        @NotNull
        static <T extends Enum<T>> Entry convert(@NotNull EnumType e) {
            Entry entry = new Entry();
            entry.setCode(e.getCode());
            entry.setDesc(e.getDesc());
            return entry;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
