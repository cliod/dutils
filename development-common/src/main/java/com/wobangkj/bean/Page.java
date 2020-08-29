package com.wobangkj.bean;

import com.wobangkj.utils.JsonUtils;
import com.wobangkj.api.Maps;
import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.BeanUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 分页封装
 *
 * @author cliod
 * @since 19-6-9
 */
@Data
@Deprecated
public final class Page<T> implements SessionSerializable {
    private static final long serialVersionUID = 7562274153136856700L;
    /**
     * 总数量
     */
    private Long count;
    /**
     * 当前页
     */
    private Integer page;
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
    public static <T> @NotNull Page<T> of(long length, int page, int size, List<T> objects) {
        Page<T> pager = new Page<>();
        pager.count = length;
        pager.size = size;
        pager.list = objects;
        pager.page = page;
        return pager;
    }

    /**
     * 静态of函数代替构造函数
     *
     * @param length 总数目
     * @param list   列表
     * @param <T>    类型
     * @return 结果
     */
    public static @NotNull <T> Page<T> of(long length, int page, List<T> list) {
        return Page.of(length, page, list.size(), list);
    }

    /**
     * 静态of函数代替构造函数
     *
     * @param length   总数目
     * @param pageable 分页
     * @param objects  列表
     * @param <T>      类型
     * @return 结果
     */
    public static <T> @NotNull Page<T> of(long length, @NotNull Pageable pageable, List<T> objects) {
        Page<T> pager = new Page<>();
        pager.count = length;
        pager.size = pageable.getSize();
        pager.page = pageable.getPage();
        pager.list = objects;
        return pager;
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
    @Deprecated
    public static @NotNull <T> Page<T> of(long length, final T... list) {
        if (BeanUtils.isNull(list)) {
            return Page.of(length, 1, 0, new ArrayList<>());
        }
        return Page.of(length, 1, list.length, Arrays.asList(list));
    }

    public static <T> @NotNull Page<T> of() {
        return Page.of(0, 1, 0, Collections.emptyList());
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
        return JsonUtils.toJson(this.toObject());
    }

    /**
     * 转成Map对象
     *
     * @return java.util.Map
     * @see java.util.Map
     */
    @Override
    public @NotNull Maps<String, Object> toObject() {
        return Maps.of("data", (Object) this.getList())
                .add("pager", Maps
                        .of("client_page", (Object) this.getPage())
                        .set("every_page", this.getSize())
                        .set("total_num", this.getCount()));
    }
}
