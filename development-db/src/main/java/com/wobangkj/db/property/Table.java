package com.wobangkj.db.property;

import com.wobangkj.api.Parser;
import org.jetbrains.annotations.NotNull;

/**
 * sql table mapper
 *
 * @author @cliod
 * @since 4/11/20 4:12 PM
 * package: com.wobangkj.db
 */
public class Table implements Parser {

    private String tableName;
    private Object model;

    public Table(@NotNull Object model) {
        //驼峰转化
        this.tableName = model.getClass().getSimpleName();
        this.model = model;
    }

    public Table(@NotNull Object model, String tableName) {
        this.tableName = tableName;
        this.model = model;
    }

    @Override
    public String parse() {
        return null;
    }
}
