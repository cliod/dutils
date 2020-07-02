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
public class ObjPageable<T> extends Pageable {

    private T param;

    public Optional<T> getParam() {
        return Optional.of(param);
    }

    public static <T> @NotNull ObjPageable<T> of(int page, int size, T obj) {
        ObjPageable<T> pageable = new ObjPageable<>();
        pageable.setParam(obj);
        pageable.setPage(page);
        pageable.setSize(size);
        return pageable;
    }

    public T get() {
        return param;
    }
}
