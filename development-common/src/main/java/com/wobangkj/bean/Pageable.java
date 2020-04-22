package com.wobangkj.bean;

import lombok.Setter;

import java.util.Objects;

/**
 * pageable
 *
 * @author @cliod
 * @since 4/22/20 11:12 AM
 * package: com.wobangkj.bean
 */
@Setter
public abstract class Pageable {

    private Integer size;

    private Integer page;

    public Integer getJpaPage() {
        return getPage() - 1;
    }

    public Integer getMybatisPage() {
        return getSize() * (getPage() - 1);
    }

    public Integer getSize() {
        return Objects.isNull(size) ? 5 : size;
    }

    public Integer getPage() {
        return Objects.isNull(page) ? 1 : page;
    }
}
