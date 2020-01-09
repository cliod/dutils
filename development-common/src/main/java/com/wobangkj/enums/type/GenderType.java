package com.wobangkj.enums.type;

import com.alibaba.fastjson.JSON;
import com.wobangkj.api.BaseType;
import com.wobangkj.api.EnumType;
import lombok.Data;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author cliod
 * @date 19-5-25
 * @desc 性别枚举
 */
@Getter
public enum GenderType implements BaseType<GenderType>, EnumType {
    /**
     * 性别
     */
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(0, "未知"),
    ;

    private Integer code;
    private String desc;
    private Map<Object, GenderType> map = new HashMap<>(16) {{
        for (GenderType value : values()) {
            put(value.code, value);
            put(value.desc, value);
            put(value.name().toLowerCase(), value);
        }
    }};

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

    @Override
    public GenderType[] list() {
        return values();
    }

    @Override
    public GenderType get(int code) {
        Optional<GenderType> value = Optional.of(map.get(code));
        return value.orElse(this);
    }

    @Override
    public GenderType get(String name) {
        Optional<GenderType> value = Optional.of(map.get(name));
        return value.orElse(this);
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
