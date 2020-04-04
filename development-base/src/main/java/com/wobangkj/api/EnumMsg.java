package com.wobangkj.api;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举类型
 *
 * @author cliod
 * @date 2019/12/27
 * package : com.wobangkj.api
 */
public interface EnumMsg extends Session {

    /**
     * 获取code
     *
     * @return code
     */
    Integer getCode();

    /**
     * 获取msg
     *
     * @return msg
     */
    String getMsg();

    /**
     * 序列化,转成字符串
     *
     * @return Json
     */
    @Override
    String toString();

    /**
     * 转化为对象(默认Map)
     *
     * @return map
     */
    @Override
    default Object toObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", this.getCode());
        map.put("msg", this.getMsg());
        return map;
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
                ", msg: \"" + this.getMsg() + '\"' +
                '}';
    }
}
