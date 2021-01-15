package com.wobangkj.domain;

import com.wobangkj.bean.Res;
import com.wobangkj.utils.BeanUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 分页、排序和模糊查询
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-08 10:59:35
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Condition extends com.wobangkj.bean.Pageable {
	/**
	 * 模糊查询
	 */
	private String key;
	/**
	 * 排序
	 */
	private String order;
	/**
	 * 分页，字段兼容
	 */
	private Integer clientPage;
	/**
	 * 分页，字段兼容
	 */
	private Integer everyPage;
	/**
	 * 匹配
	 */
	private Map<String, Object> match;
	/**
	 * 大小
	 */
	private List<Among<?>> among;
	/**
	 * 原生sql查询(条件)，可能会发生sql注入
	 */
	private List<Query> queries;

	/**
	 * 关联sql查询语句
	 *
	 * @param related 关联词
	 * @param sql     query查询
	 */
	protected void addQuery(String related, String sql) {
		if (BeanUtils.isEmpty(this.queries)) {
			queries = new ArrayList<>();
		}
		this.queries.add(Query.builder().related(related).query(sql).build());
	}

	/**
	 * 添加and查询条件
	 *
	 * @param sql 条件
	 */
	public void andQuery(String sql) {
		this.addQuery("and", sql);
	}

	/**
	 * 添加or查询条件
	 *
	 * @param sql 条件
	 */
	public void orQuery(String sql) {
		this.addQuery("or", sql);
	}

	/**
	 * 添加and =查询条件
	 *
	 * @param column 字段
	 * @param value  值
	 */
	public void andMatch(String column, Object value) {
		if (Objects.isNull(match)) {
			match = Res.empty();
		}
		match.put(column, value);
	}

	/**
	 * 添加范围查询条件
	 *
	 * @param among 范围
	 */
	public void andAmong(Among<?> among) {
		if (Objects.isNull(this.among)) {
			this.among = new ArrayList<>();
		}
		this.among.add(among);
	}

	@Override
	public Integer getSize() {
		if (Objects.nonNull(everyPage) && everyPage != 0) {
			return everyPage;
		} else {
			return super.getSize();
		}
	}

	@Override
	public void setSize(Integer size) {
		this.everyPage = size;
		super.setSize(size);
	}

	@Override
	public Integer getPage() {
		if (Objects.nonNull(clientPage) && clientPage != 0) {
			return clientPage;
		} else {
			return super.getPage();
		}
	}

	@Override
	public void setPage(Integer page) {
		this.clientPage = page;
		super.setPage(page);
	}

	/**
	 * 克隆
	 *
	 * @return 结果
	 */
	@Override
	protected Condition clone() throws CloneNotSupportedException {
		return (Condition) super.clone();
	}
}
