package com.wobangkj.bean;

import com.wobangkj.api.IRes;
import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.BeanUtils;
import com.wobangkj.utils.JsonUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * 旧版分页封装
 *
 * @author cliod
 * @since 19-6-9
 */
@Data
@Deprecated
public class Page<T> implements IRes, SessionSerializable {

	public static Page<?> EMPTY = Page.of(0, 1, 10, Collections.emptyList());

	private static final long serialVersionUID = 7562274153136856700L;
	/**
	 * 总数量
	 */
	private Long count;
	/**
	 * 分页
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
	 * @param page    当前页数
	 * @param size    当前数目
	 * @param objects 列表
	 * @param <T>     类型
	 * @return 结果
	 */
	public static @NotNull <T> Page<T> of(long length, int page, int size, Collection<T> objects) {
		Page<T> res = new Page<>();
		res.count = length;
		res.page = page;
		res.size = size;
		res.list = new ArrayList<>(objects);
		return res;
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
	public static @NotNull <T> Page<T> of(long length, @NotNull Pageable pageable, List<T> objects) {
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
	public static @NotNull <T> Page<T> of(long length, Collection<T> list) {
		return Page.of(length, 1, list.size(), list);
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
		if (Objects.isNull(list)) {
			return Page.of(length, 1, 0, new ArrayList<>());
		}
		return Page.of(length, 1, list.length, Arrays.asList(list));
	}

	@SuppressWarnings("unchecked")
	public static <T> @NotNull Page<T> of() {
		return (Page<T>) EMPTY;
	}

	/**
	 * 从pager转化
	 *
	 * @param pager pager对象
	 * @param <T>   类型
	 * @return page对象
	 */
	public static @NotNull <T> Page<T> ofPage(Pager<T> pager) {
		return Page.of(pager.getTotalNum(), pager.getClientPage(), pager.getEveryPage(), pager.getData());
	}

	/**
	 * 转成pager对象
	 *
	 * @return pager
	 */
	public Pager<T> toPager() {
		return Pager.of(this.getCount(), Pageable.of(this.getPage(), this.getSize()), this.getList());
	}

	/**
	 * 转成Res[Map]对象
	 *
	 * @return Map
	 */
	@Override
	public Res toRes() {
		return toPager().toObject();
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
	 * 转成对象
	 *
	 * @return obj
	 */
	@Override
	public Res toObject() {
		return toRes();
	}

	/**
	 * 转成Json
	 *
	 * @return Json
	 */
	@Override
	public String toJson() {
		return JsonUtils.toJson(this.toObject());
	}
}
