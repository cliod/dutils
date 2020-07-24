package com.wobangkj.api;

/**
 * 获取样式
 *
 * @author cliod
 * @since 7/8/20 5:02 PM
 */
public interface Format<T> extends ValueWrapper<T> {
    /**
     * 获取样式
     *
     * @return 样式
     */
    T getPattern();

    /**
     * 获取内容
     *
     * @return 值
     */
    default T value() {
        return getPattern();
    }
}
