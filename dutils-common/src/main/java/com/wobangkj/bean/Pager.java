package com.wobangkj.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wobangkj.api.SessionSerializable;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * 分页封装
 *
 * @author cliod
 * @since 19-6-9
 */
@Data
public final class Pager<T> implements SessionSerializable {

	private static final long serialVersionUID = 7562274153136856700L;
	public static Pager<?> EMPTY = Pager.of(0, 1, 10, Collections.emptyList());
	/**
	 * 总数量
	 */
	private Long totalNum;
	/**
	 * 当前页
	 */
	private Integer clientPage;
	/**
	 * 当前数量
	 */
	private Integer everyPage;
	/**
	 * 列表
	 */
	private List<T> data;

	/**
	 * 静态of函数代替构造函数
	 *
	 * @param totalNum 总数目
	 * @param size     当前数目
	 * @param objects  列表
	 * @param <T>      类型
	 * @return 结果
	 */
	public static <T> @NotNull Pager<T> of(long totalNum, int page, int size, List<T> objects) {
		Pager<T> pager = new Pager<>();
		pager.totalNum = totalNum;
		pager.everyPage = size;
		pager.clientPage = page;
		pager.data = objects;
		return pager;
	}

	/**
	 * 静态of函数代替构造函数
	 *
	 * @param totalNum 总数目
	 * @param list     列表
	 * @param <T>      类型
	 * @return 结果
	 */
	public static <T> @NotNull Pager<T> of(long totalNum, int page, List<T> list) {
		return Pager.of(totalNum, page, list.size(), list);
	}

	/**
	 * 静态of函数代替构造函数
	 *
	 * @param totalNum 总数目
	 * @param pageable 分页
	 * @param objects  列表
	 * @param <T>      类型
	 * @return 结果
	 */
	public static <T> @NotNull Pager<T> of(long totalNum, @NotNull Pageable pageable, List<T> objects) {
		Pager<T> pager = new Pager<>();
		pager.totalNum = totalNum;
		pager.everyPage = pageable.getSize();
		pager.clientPage = pageable.getPage();
		pager.data = objects;
		return pager;
	}

	/**
	 * 返回默认空分页
	 *
	 * @param <T> 类型
	 * @return 结果
	 * @see Pager#empty()
	 */
	@Deprecated
	public static <T> @NotNull Pager<T> of() {
		return empty();
	}

	/**
	 * 返回空数组
	 *
	 * @param <T> 泛型
	 * @return 结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> @NotNull Pager<T> empty() {
		return (Pager<T>) EMPTY;
	}

	/**
	 * 转成字符串
	 *
	 * @return 字符串
	 */
	@Override
	public @NotNull
	String toString() {
		return this.toJson();
	}

	/**
	 * 转成Map对象
	 *
	 * @return java.util.Map
	 * @see java.util.Map
	 */
	@Override
	public @NotNull
	Res toObject() {
		return Res.of("data", this.getData())
				.add("pager", Res
						.of("client_page", this.getClientPage())
						.set("every_page", this.getEveryPage())
						.set("total_num", this.getTotalNum()));
	}

	@Deprecated
	@JsonIgnore
	public Long getCount() {
		return totalNum;
	}

	@Deprecated
	@JsonIgnore
	public void setCount(Long count) {
		this.totalNum = count;
	}

	@Deprecated
	@JsonIgnore
	public Integer getPage() {
		return clientPage;
	}

	@Deprecated
	@JsonIgnore
	public void setPage(Integer page) {
		this.clientPage = page;
	}

	@Deprecated
	@JsonIgnore
	public Integer getSize() {
		return everyPage;
	}

	@Deprecated
	@JsonIgnore
	public void setSize(Integer size) {
		this.everyPage = size;
	}

	@Deprecated
	@JsonIgnore
	public List<T> getList() {
		return data;
	}

	@Deprecated
	@JsonIgnore
	public void setList(List<T> list) {
		this.data = list;
	}

}
