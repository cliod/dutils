package com.wobangkj.domain;

import lombok.SneakyThrows;

import java.util.List;

/**
 * 分页、排序和模糊查询
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 10:59:35
 */
@Deprecated
public final class Pageable extends Condition {

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

	/**
	 * 原生sql查询(条件)，可能会导致sql注入，不建议使用
	 *
	 * @param queries 原生查询
	 */
	@Override
	public void setQueries(List<Query> queries) {
		super.setQueries(queries);
	}

	/**
	 * 关联sql查询语句
	 *
	 * @param related 关联词
	 * @param sql     query查询
	 */
	@Override
	@Deprecated
	public void addQuery(String related, String sql) {
		super.addQuery(related, sql);
	}

	@Override
	@Deprecated
	public void andQuery(String sql) {
		super.andQuery(sql);
	}

	@Override
	@Deprecated
	public void orQuery(String sql) {
		super.orQuery(sql);
	}
}
