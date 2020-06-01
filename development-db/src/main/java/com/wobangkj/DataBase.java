package com.wobangkj;

import com.wobangkj.api.Crud;
import org.jetbrains.annotations.NotNull;

/**
 * database operation
 *
 * @author @cliod
 * @since 4/11/20 4:13 PM
 * package: com.wobangkj.db
 */
public class DataBase {

    public static @NotNull Crud crud(Class<?> type) {
        return new CrudImpl(type);
    }
}
