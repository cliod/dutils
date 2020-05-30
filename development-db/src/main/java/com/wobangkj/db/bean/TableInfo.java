package com.wobangkj.db.bean;

import com.wobangkj.db.property.Table;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * table info
 *
 * @author @cliod
 * @since 5/30/20 2:44 PM
 * package: com.wobangkj.db.bean
 */
@Getter
public class TableInfo {

    private String tableName;
    private String alias;
    private List<String> conditions;

    private List<String> ignores;

    private transient Table table;

    public TableInfo(Table table) {
        this.table = table;
    }

    private TableInfo() {
    }

    public static @NonNull @NotNull TableInfo of(String tableName, String alias, List<String> conditions, String... ignores) {
        return new TableInfo().tableName(tableName).alias(alias).conditions(conditions).ignores(Arrays.asList(ignores));
    }

    public static @NonNull @NotNull TableInfo of(String tableName, String alias, String... ignores) {
        return of(tableName, alias, null, ignores);
    }

    public TableInfo tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public TableInfo alias(String alias) {
        this.alias = alias;
        return this;
    }

    public TableInfo conditions(List<String> conditions) {
        this.conditions = conditions;
        return this;
    }

    public TableInfo ignores(List<String> ignores) {
        this.ignores = ignores;
        return this;
    }

    public Table call() {
        return table;
    }

}
