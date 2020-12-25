package com.wobangkj.bean;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * 统一对象分页传参，主要用于Post请求分页， 继承于 Pageable
 *
 * @author @cliod
 * @since 4/22/20 11:15 AM
 * @see com.wobangkj.bean.Pageable
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
}
