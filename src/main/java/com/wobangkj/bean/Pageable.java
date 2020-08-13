package com.wobangkj.bean;

import lombok.Setter;
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
public abstract class Pageable {

    private Integer size;
    private Integer page;

    /**
     * 静态方法
     *
     * @return 结果
     */
    public static @NotNull Pageable of() {
        return of(1, 10);
    }

    /**
     * 静态方法
     *
     * @param page 分页
     * @param size 大小
     * @return 结果
     */
    public static @NotNull Pageable of(int page, int size) {
        return new Pageable() {{
            setPage(page);
            setSize(size);
        }};
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

    public Integer getSize() {
        return Objects.isNull(size) ? 5 : size;
    }

    public Integer getPage() {
        return Objects.isNull(page) ? 1 : page;
    }

}
