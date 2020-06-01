package com.wobangkj;

import com.wobangkj.api.Crud;
import com.wobangkj.api.Pageable;
import com.wobangkj.db.QuerySql;
import com.wobangkj.db.Sql;
import com.wobangkj.db.bean.ColumnInfo;
import com.wobangkj.db.property.Column;
import com.wobangkj.db.property.Condition;
import com.wobangkj.db.property.Example;
import com.wobangkj.db.property.Table;
import com.wobangkj.utils.BeanUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * crud
 *
 * @author @cliod
 * @since 5/30/20 3:51 PM
 * package: com.wobangkj
 */
@Slf4j
public class CrudImpl implements Crud {

    private final Class<?> type;
    private Column column;

    private JdbcTemplate template;

    private Sql query;
    private Sql update;
    private Sql insert;
    private Sql delete;

    @SneakyThrows
    public CrudImpl(Class<?> type) {
        this.type = type;
        this.column = new Column("", type.newInstance());
        query = new QuerySql(new Table(type), column, new Condition(), new Example());
    }

    @Override
    public Object findById(Object id) {
        return null;
    }

    @Override
    public List<?> findAll(Object t, Pageable pageable) {
        return template.query(query.parse(), (rs, rowNum) -> {
            Object obj = null;
            try {
                obj = type.newInstance();
                for (ColumnInfo col : column.getColumns()) {
                    BeanUtils.setFieldValue(obj, col.getAlias(), rs.getString(col.getAlias()));
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return obj;
        });
    }

    @Override
    public Object findOne(Object obj) {
        return null;
    }

    @Override
    public Object insert(Object obj) {
        template.execute(insert.parse());
        return this.findOne(obj);
    }

    @SneakyThrows
    @Override
    public Object update(Object obj) {
        template.update(update.parse());
        return this.findById(BeanUtils.getFieldValue(obj, "id"));
    }

    @Override
    public int deleteById(Object id) {
        this.template.execute(delete.parse());
        return 1;
    }
}
