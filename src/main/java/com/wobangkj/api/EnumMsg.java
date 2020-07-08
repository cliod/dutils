package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.exception.AppException;
import com.wobangkj.utils.JsonUtils;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
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
        return Maps.of("code", (Object) getCode()).add("msg", getMsg());
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

    default Throwable toThrowable() {
        return new AppException(getCode(), getMsg());
    }
}
