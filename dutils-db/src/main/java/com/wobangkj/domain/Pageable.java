package com.wobangkj.domain;

import lombok.*;

import java.util.Objects;

/**
 * 分页、排序和模糊查询
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 10:59:35
 */
@Deprecated
public class Pageable extends Condition {

	protected static Pageable DEFAULT = new Pageable();

	@SneakyThrows
	public static Pageable of(int clientPage, int everyPage) {
		return of(clientPage, everyPage, "", "");
	}

	@SneakyThrows
	public static Pageable of(int clientPage, int everyPage, String order, String key) {
		Pageable pageable = DEFAULT.clone();
		pageable.setSize(everyPage);
		pageable.setPage(clientPage);
		pageable.setOrder(order);
		pageable.setKey(key);
		return pageable;
	}

	/**
	 * 克隆
	 *
	 * @return 结果
	 */
	@Override
	protected Pageable clone() throws CloneNotSupportedException {
		return (Pageable) super.clone();
	}
}
