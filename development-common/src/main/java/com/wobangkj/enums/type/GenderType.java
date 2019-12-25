package com.wobangkj.enums.type;

import com.alibaba.fastjson.JSON;
import com.wobangkj.api.BaseType;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author cliod
 * @date 19-5-25
 * @desc 性别枚举
 */
@Getter
public enum GenderType implements BaseType<GenderType> {
    /**
     * 性别
     */
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(0, "未知"),
    ;

    private Integer code;
    private String desc;

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
    public GenderType[] list() {
        return values();
    }

    @Override
    public GenderType get(int code) {
        GenderType[] values = values();
        for (GenderType value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return values[0];
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
        static <T extends Enum<T>> Entry convert(@NotNull BaseType<T> e) {
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
