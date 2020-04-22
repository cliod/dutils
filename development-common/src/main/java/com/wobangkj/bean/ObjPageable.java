package com.wobangkj.bean;

import lombok.Setter;

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
}
