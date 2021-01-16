package com.wobangkj.bean;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * 统一对象分页传参，主要用于Post请求分页， 继承于 Pageable
 *
 * @author @cliod
 * @see com.wobangkj.bean.Pageable
 * @since 4/22/20 11:15 AM
 */
@Setter
public class ObjPager<T> extends Pageable {

	private T data;

	public static <T> @NotNull ObjPager<T> of(int page, int size, T obj) {
		ObjPager<T> pageable = new ObjPager<>();
		pageable.setData(obj);
		pageable.setPage(page);
		pageable.setSize(size);
		return pageable;
	}

	public final @NotNull Optional<T> getData() {
		return Optional.of(get());
	}

	public T get() {
		return data;
	}

	/**
	 * 转成Res[Map]对象
	 *
	 * @return Map
	 */
	@Override
	public Res toRes() {
		return super.toRes().add("data", this.get());
	}
}
