package com.wobangkj.api;

import org.jetbrains.annotations.NotNull;

/**
 * @author @cliod
 * @since 5/30/20 1:42 PM
 * package: com.example.demo.db.api
 */
public interface Pageable {

    static @NotNull Pageable of(long offset, long limit) {
        return new Pageable() {
            @Override
            public long getPage() {
                return offset >= 0 ? offset : 0L;
            }

            @Override
            public long getSize() {
                return limit > 0 ? limit : 10L;
            }
        };
    }

    static @NotNull Pageable of(long offset) {
        return new Pageable() {
            @Override
            public long getPage() {
                return offset >= 0 ? offset : 0L;
            }

            @Override
            public long getSize() {
                return 10L;
            }
        };
    }

    static @NotNull Pageable limit(long limit) {
        return new Pageable() {
            @Override
            public long getPage() {
                return 0L;
            }

            @Override
            public long getSize() {
                return limit > 0 ? limit : 10L;
            }
        };
    }

    default long getPage() {
        return 0L;
    }

    default long getSize() {
        return 10L;
    }

}
