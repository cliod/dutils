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

    /**
     * 静态方法
     *
     * @param page 分页
     * @param size 大小
     * @return 结果
     */
    public static @NotNull Pageable of(int page, int size) {
        return Pager.of(page, size);
    }

    /**
     * 内部类实现
     */
    public static class Pager extends Pageable {

        public static @NotNull Pager of() {
            return new Pager();
        }

        public static @NotNull Pager of(int page, int size) {
            return of().page(page).size(size);
        }

        public Pager page(int page) {
            setPage(page);
            return this;
        }

        public Pager size(int size) {
            setSize(size);
            return this;
        }
    }
}
