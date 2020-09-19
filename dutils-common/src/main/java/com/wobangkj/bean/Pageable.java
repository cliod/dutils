package com.wobangkj.bean;

import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * pageable
 *
 * @author @cliod
 * @since 4/22/20 11:12 AM
 * package: com.wobangkj.bean
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
		return Objects.isNull(size) ? 10 : size;
	}

	@Override
	public Integer getPage() {
		return Objects.isNull(page) ? 1 : page;
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
