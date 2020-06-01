package com.wobangkj.db.bean;

import com.wobangkj.db.property.Column;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * column info
 *
 * @author @cliod
 * @since 6/1/20 9:48 AM
 * package: com.wobangkj.db.bean
 */
@Getter
public class ColumnInfo {
    /**
     * 实际字段名
     */
    private String columnName;
    /**
     * 查找别名
     */
    private String alias;
    /**
     * 字段类型
     */
    private Class<?> type;

    private transient Column column;

    public ColumnInfo(Column column) {
        this.column = column;
    }

    public static @NotNull ColumnInfo of(Column column, String alias, Class<?> type) {
        return of(column, alias, alias, type);
    }

    public static @NotNull ColumnInfo of(Column column, String columnName, String alias, Class<?> type) {
        ColumnInfo info = new ColumnInfo(column);
        info.column = column;
        info.columnName = columnName;
        info.alias = alias;
        info.type = type;
        return info;
    }

    public ColumnInfo columnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public ColumnInfo alias(String alias) {
        this.alias = alias;
        return this;
    }

    public ColumnInfo type(Class<?> type) {
        this.type = type;
        return this;
    }

    public Column call() {
        return column;
    }

}
