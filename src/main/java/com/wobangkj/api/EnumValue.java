package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.utils.JsonUtils;

/**
 * 值 - 枚举
 *
 * @author cliod
 * @since 7/8/20 3:33 PM
 */
public interface EnumValue<T> extends ValueWrapper<T> {

    /**
     * 枚举值
     *
     * @return 值
     */
    T value();

    @Override
    default Object toObject() {
        return Maps.of("value", value());
    }

    @Override
    default String toJson() {
        return JsonUtils.toJson(this.toObject());
    }
}
