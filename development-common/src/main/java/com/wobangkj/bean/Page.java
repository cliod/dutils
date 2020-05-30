package com.wobangkj.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wobangkj.api.Session;
import com.wobangkj.utils.BeanUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 分页封装
 *
 * @author cliod
 * @since 19-6-9
 */
@Data
public final class Page<T> implements Session {
    private static final long serialVersionUID = 7562274153136856700L;
    /**
     * 总数量
     */
    private Long count;
    /**
     * 当前数量
     */
    private Integer size;
    /**
     * 列表
     */
    private List<T> list;

    /**
     * 静态of函数代替构造函数
     *
     * @param length  总数目
     * @param size    当前数目
     * @param objects 列表
     * @param <T>     类型
     * @return 结果
     */
    public static <T> @NotNull Page<T> of(long length, int size, List<T> objects) {
        Page<T> page = new Page<>();
        page.count = length;
        page.size = size;
        page.list = objects;
        return page;
    }

    /**
     * 静态of函数代替构造函数
     *
     * @param length 总数目
     * @param list   列表
     * @param <T>    类型
     * @return 结果
     */
    public static @NotNull <T> Page<T> of(long length, List<T> list) {
        return Page.of(length, list.size(), list);
    }

    /**
     * 静态of函数代替构造函数
     *
     * @param length 总数目
     * @param list   列表
     * @param <T>    类型
     * @return 结果
     */
    @SafeVarargs
    public static @NotNull <T> Page<T> of(long length, final T... list) {
        if (BeanUtils.isNull(list)) {
            return Page.of(length, 0, new ArrayList<>());
        }
        return Page.of(length, list.length, Arrays.asList(list));
    }

    /**
     * 转成字符串
     *
     * @return 字符串
     */
    @Override
    public @NotNull String toString() {
        return this.toJson();
    }

    /**
     * 转成Json
     *
     * @return Json
     */
    @Override
    public @NotNull String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 转成Map对象
     *
     * @return java.util.Map
     * @see java.util.Map
     */
    @Override
    public Map<String, Object> toObject() {
        return JSONObject.parseObject(this.toJson());
    }
}
