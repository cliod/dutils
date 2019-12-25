package com.wobangkj.api;

import java.util.HashMap;

/**
 * @author cliod
 * @date 19-6-22
 * @desc base type
 */
public interface BaseType<E extends Enum<E>> extends Session {

    /**
     * 获取code
     *
     * @return code
     */
    Integer getCode();

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getDesc();

    /**
     * 序列化,转成字符串
     *
     * @return Json
     */
    @Override
    String toString();

    /**
     * 获取所有枚举
     *
     * @return 数组
     */
    E[] list();

    /**
     * 获取一个
     *
     * @param code code
     * @return 一个
     */
    default E get(int code) {
        return list()[0];
    }

    /**
     * 获取一个
     *
     * @param name 名字
     * @return 一个
     */
    default E get(String name) {
        E[] values = list();
        for (E value : values) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return values[0];
    }

    /**
     * 转化为对象(默认Map)
     *
     * @return map
     */
    @Override
    default Object toObject() {
        return new HashMap<String, Object>(4) {{
            put("code", getCode());
            put("desc", getDesc());
        }};
    }

    /**
     * 序列化,转成Json
     *
     * @return Json
     */
    @Override
    default String toJson() {
        return "{" +
                "code: " + this.getCode() +
                ", desc: \"" + this.getDesc() + '\"' +
                '}';
    }
}
