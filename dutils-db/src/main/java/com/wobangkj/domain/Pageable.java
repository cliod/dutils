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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pageable extends com.wobangkj.bean.Pageable {

	protected static Pageable DEFAULT = new Pageable();

	private String order;
	private String key;
	private Integer clientPage;
	private Integer everyPage;

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
	protected Pageable clone() throws CloneNotSupportedException {
		return (Pageable) super.clone();
	}
}
