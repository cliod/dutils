package com.wobangkj.db.property;

import com.google.common.base.CaseFormat;
import com.wobangkj.api.Parser;
import com.wobangkj.db.bean.TableInfo;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * sql table mapper
 *
 * @author @cliod
 * @since 4/11/20 4:12 PM
 * package: com.wobangkj.db
 */
@Getter
public class Table implements Parser {

    private final String tableName;
    private final Class<?> type;
    private final StringBuilder builder;
    private String alias;
    private List<TableInfo> innerTables;
    private List<TableInfo> leftTables;
    private List<TableInfo> rightTables;
    private String cache;

    public Table(@NotNull Class<?> type) {
        //驼峰转化
        this.type = type;
        this.tableName = getTableName();
        builder = new StringBuilder();
    }

    public Table(@NotNull Class<?> type, String tableName) {
        this.tableName = tableName;
        this.type = type;
        builder = new StringBuilder();
    }

    /**
     * inner join
     *
     * @param tableNames 格式，name:alias 或者name
     * @return table
     */
    public Table innerTables(String tableName, String... tableNames) {
        this.table(innerTables, tableName, tableNames);
        return this;
    }

    public TableInfo inner() {
        return new TableInfo(this);
    }

    public TableInfo left() {
        return new TableInfo(this);
    }

    public TableInfo right() {
        return new TableInfo(this);
    }

    public Table leftTable(String tableName, String... tableNames) {
        this.table(leftTables, tableName, tableNames);
        return this;
    }

    public Table rightTable(String tableName, String... tableNames) {
        table(rightTables, tableName, tableNames);
        return this;
    }

    @Override
    public String parse() {
        if (StringUtils.isNotBlank(cache)) {
            return cache;
        }
        builder.append("`").append(tableName).append("`");
        if (StringUtils.isNotBlank(alias))
            builder.append(" ").append(alias);
        parse(innerTables, " inner join ");
        parse(leftTables, " left join ");
        parse(rightTables, " right join ");
        cache = builder.toString();
        return cache;
    }

    /**
     * 在用户未定义下获取表名
     *
     * @return 表名
     */
    protected String getTableName() {
        return toCamel(type.getSimpleName());
    }

    private void parse(List<TableInfo> tableInfos, String link) {
        if (CollectionUtils.isNotEmpty(tableInfos)) {
            builder.append(link);
            for (TableInfo info : tableInfos) {
                builder.append("`").append(info.getTableName()).append("`");
                if (StringUtils.isNotBlank(info.getAlias())) {
                    builder.append(" ").append(info.getAlias());
                }
            }
        }
    }

    private void table(List<TableInfo> tables, String tableName, String... tableNames) {
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        String[] ts;
        if (tableName.contains(":")) {
            ts = tableName.split(":");
            tables.add(TableInfo.of(ts[0], ts[1]));
        } else
            tables.add(TableInfo.of(tableName.trim(), ""));
        if (Objects.isNull(tableNames)) {
            return;
        }
        for (String table : tableNames) {
            if (table.contains(":")) {
                ts = table.split(":");
                tables.add(TableInfo.of(ts[0].trim(), ts[1].trim()));
            } else {
                tables.add(TableInfo.of(table.trim(), ""));
            }
        }
    }


    private String toCamel(String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, value);
    }

    private String toUnderscore(String value) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, value);
    }
}
