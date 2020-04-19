package com.wobangkj.db.property;

import org.jetbrains.annotations.NotNull;

/**
 * sql table mapper
 *
 * @author @cliod
 * @since 4/11/20 4:12 PM
 * package: com.wobangkj.db
 */
public class Table {

    private String tableName;
    private Object model;

    public Table(@NotNull Object model) {
        this.tableName = model.getClass().getSimpleName();
        this.model = model;
    }

    public Table(@NotNull Object model, String tableName) {
        this.tableName = tableName;
        this.model = model;
    }
}
