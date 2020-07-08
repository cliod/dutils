package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.utils.JsonUtils;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 * package : com.wobangkj.api
 */
public interface EnumType extends Session {

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
     * 根据数值获取对象
     *
     * @param code 数值
     * @return 对象
     */
    EnumType get(Integer code);

    /**
     * 根据名字或者描述获取对象
     *
     * @param name 名字或者描述
     * @return 对象
     */
    default EnumType get(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * 转化为对象(默认Map)
     *
     * @return map
     */
    @Override
    default Object toObject() {
        return Maps.of("code", (Object) getCode()).add("desc", getDesc());
    }

    /**
     * 序列化,转成Json
     *
     * @return Json
     */
    @Override
    default String toJson() {
        return JsonUtils.toJson(this.toObject());
    }
}
