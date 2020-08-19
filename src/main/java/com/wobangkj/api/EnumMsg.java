package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.exception.AppException;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 * package : com.wobangkj.api
 */
public interface EnumMsg extends SessionSerializable {

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
     * 转化为对象(默认Map)
     *
     * @return map
     */
    @Override
    default Object toObject() {
        return Maps.of("code", (Object) getCode()).add("msg", getMsg());
    }

    default Throwable toThrowable() {
        return new AppException(getCode(), getMsg());
    }
}
