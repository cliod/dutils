package com.wobangkj.domain;

import lombok.*;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Condition extends com.wobangkj.bean.Pageable {

	private String order;
	private String key;
	private Integer clientPage;
	private Integer everyPage;
	/**
	 * 匹配
	 */
	private Map<String, Object> match;
	/**
	 * 大小
	 */
	private List<Among<?>> among;

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
