package com.wobangkj.bean;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * pageable
 *
 * @author @cliod
 * @since 4/22/20 11:15 AM
 * package: com.wobangkj.bean
 */
@Setter
public class ObjPager<T> extends Pageable {

    private T param;

    public @NotNull
    final Optional<T> getParam() {
        return Optional.of(get());
    }

    public static <T> @NotNull ObjPager<T> of(int page, int size, T obj) {
        ObjPager<T> pageable = new ObjPager<>();
        pageable.setParam(obj);
        pageable.setPage(page);
        pageable.setSize(size);
        return pageable;
    }

    public T get() {
        return param;
    }
}
