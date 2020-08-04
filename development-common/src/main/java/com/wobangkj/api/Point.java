package com.wobangkj.api;

/**
 * 点
 *
 * @author @cliod
 * @since 4/8/20 9:17 AM
 * package: com.wobangkj.api
 */
public interface Point<X, Y> extends Dimension {

    /**
     * 获取x的值
     *
     * @return x
     */
    X getX();

    /**
     * 获取y的值
     *
     * @return y
     */
    Y getY();
}
