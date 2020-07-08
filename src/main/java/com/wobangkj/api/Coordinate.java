package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.utils.JsonUtils;

/**
 * 点
 *
 * @author @cliod
 * @since 4/8/20 9:17 AM
 * package: com.wobangkj.api
 */
public interface Coordinate<X, Y, Z> extends Dimension {

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

    /**
     * 获取z的值
     *
     * @return z
     */
    Z getZ();

    /**
     * 获取维度
     *
     * @return 维度
     */
    default int getDimension() {
        return 2;
    }

    /**
     * 获取座标点
     *
     * @return 座标点
     */
    default String getPoint() {
        return String.format("(%s,%s,%s)", getX(), getY(), getZ());
    }

    @Override
    default String toJson() {
        return JsonUtils.toJson(this.toObject());
    }

    @Override
    default Object toObject() {
        return Maps.of("x", (Object) getX()).add("y", getY()).add("z", getZ());
    }
}
