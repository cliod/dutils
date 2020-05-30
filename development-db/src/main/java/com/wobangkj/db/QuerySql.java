package com.wobangkj.db;

import com.wobangkj.db.property.Column;
import com.wobangkj.db.property.Condition;
import com.wobangkj.db.property.Example;
import com.wobangkj.db.property.Table;
import org.apache.commons.lang3.StringUtils;

/**
 * query sql
 *
 * @author @cliod
 * @since 5/30/20 3:41 PM
 * package: com.wobangkj.db
 */
public class QuerySql extends Sql {

    private final StringBuilder builder;
    private Table table;
    private Column column;
    private Condition condition;
    private Example example;
    private transient String cache;

    public QuerySql(Table table, Column column, Condition condition, Example example) {
        this.table = table;
        this.column = column;
        this.condition = condition;
        this.example = example;
        this.builder = new StringBuilder();
    }

    @Override
    public String parse() {
        if (StringUtils.isNotBlank(cache)) return cache;
        builder.append("select ").append(column.parse()).append(" from ").append(table.parse());
        builder.append(" where ").append(example.parse()).append(condition.parse());
        cache = builder.toString();
        return cache;
    }
}
