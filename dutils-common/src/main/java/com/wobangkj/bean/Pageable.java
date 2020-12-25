package com.wobangkj.bean;

import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 分页封装
 *
 * @author @cliod
 * @since 4/22/20 11:12 AM
 */
@Setter
public abstract class Pageable implements com.wobangkj.api.Pageable, Cloneable {

	public static Pageable DEFAULT = new Pageable() {{
		setPage(1);
		setSize(10);
	}};

	private Integer size;
	private Integer page;

	/**
	 * 静态方法
	 *
	 * @return 结果
	 */
	public static @NotNull Pageable of() {
		return DEFAULT;
	}

	/**
	 * 静态方法
	 *
	 * @param page 分页
	 * @param size 大小
	 * @return 结果
	 */
	@SneakyThrows
	public static @NotNull Pageable of(int page, int size) {
		Pageable pageable = DEFAULT.clone();
		pageable.setPage(page);
		pageable.setSize(size);
		return pageable;
	}

	public final @NotNull Integer getJpaPage() {
		return getPage() - 1;
	}

	public final @NotNull Integer getMybatisPage() {
		return getSize() * (getPage() - 1);
	}

	public final @NotNull Integer getOffset() {
		return getSize() * (getPage() - 1);
	}

	public final @NotNull Integer getLimit() {
		return getSize();
	}

	@Override
	public Integer getSize() {
		if (Objects.isNull(size)) {
			return 5;
		} else if (size > this.getSizeMax()) {
			return this.getSizeMax();
		} else {
			return size;
		}
	}

	@Override
	public Integer getPage() {
		if (Objects.isNull(page)) {
			return 1;
		} else if (page < this.minPageSize()) {
			return this.minPageSize();
		} else {
			return page;
		}
	}

	/**
	 * 最大分页
	 *
	 * @return 条数
	 */
	protected int getSizeMax() {
		return 200;
	}

	/**
	 * 最小分页
	 *
	 * @return 页数
	 */
	protected int minPageSize() {
		return 1;
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
