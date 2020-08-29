package com.wobangkj.api;

import java.io.Serializable;

/**
 * 会话
 *
 * @author cliod
 * @since 2019/11/9
 * package : com.wobangkj.api
 */
@Deprecated
public interface Session extends Serializable {

    /**
     * toString 方法,一定的重写toString()方法
     *
     * @return json
     */
    @Override
    String toString();

    /**
     * 转成对象
     *
     * @return obj
     */
    default Object toObject() {
        return this;
    }

    /**
     * 对象转成json
     *
     * @return json
     */
    default String toJson() {
        throw new UnsupportedOperationException();
    }
}
