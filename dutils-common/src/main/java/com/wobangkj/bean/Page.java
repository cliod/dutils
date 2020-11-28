package com.wobangkj.bean;

import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.JsonUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 旧版分页封装
 *
 * @author cliod
 * @since 11/28/20 3:06 PM
 */
@Data
@Deprecated
public class Page<T> implements SessionSerializable {

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
	private Collection<T> list;

	/**
	 * 静态of函数代替构造函数
	 *
	 * @param length  总数目
	 * @param size    当前数目
	 * @param objects 列表
	 * @param <T>     类型
	 * @return 结果
	 */
	public static @NotNull <T> Page<T> of(long length, int size, Collection<T> objects) {
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
	public static @NotNull <T> Page<T> of(long length, Collection<T> list) {
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
	@Deprecated
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
	public String toString() {
		return this.toJson();
	}

	/**
	 * 转成Json
	 *
	 * @return Json
	 */
	@Override
	public String toJson() {
		return JsonUtils.toJson(this);
	}

}
