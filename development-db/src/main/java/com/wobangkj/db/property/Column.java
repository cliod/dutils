package com.wobangkj.db.property;

import com.wobangkj.api.Parser;
import com.wobangkj.db.bean.ColumnInfo;
import com.wobangkj.utils.BeanUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * sql colnum mapper
 *
 * @author @cliod
 * @since 4/11/20 4:12 PM
 * package: com.wobangkj.db
 */
@Getter
public class Column implements Parser {

    private final String prefix;
    private final StringBuilder builder;
    protected List<ColumnInfo> columns;
    private List<String> ignores;
    private String cache;

    public Column(String prefix, Object obj) {
        this.prefix = prefix;
        this.columns = new ArrayList<>();
        for (Map.Entry<String, Class<?>> entry : BeanUtils.getFieldNameAndType(obj).entrySet()) {
            columns.add(ColumnInfo.of(this, entry.getKey(), entry.getValue()));
        }
        this.builder = new StringBuilder();
    }

    public Column(String prefix, Object obj, String... ignore) {
        this.prefix = prefix;
        for (Map.Entry<String, Class<?>> entry : BeanUtils.getFieldNameAndType(obj).entrySet()) {
            columns.add(ColumnInfo.of(this, entry.getKey(), entry.getValue()));
        }
        this.ignores = Arrays.asList(ignore);
        this.builder = new StringBuilder();
    }

    @Override
    public String parse() {
        if (StringUtils.isNotBlank(cache)) {
            return cache;
        }
        for (ColumnInfo info : columns) {
            if (StringUtils.isNotBlank(prefix))
                builder.append("`").append(prefix).append("`").append(".");
            builder.append("`").append(info.getColumnName()).append("`");
        }
        cache = builder.toString();
        return cache;
    }

    @Override
    public String toString() {
        return parse();
    }
}
