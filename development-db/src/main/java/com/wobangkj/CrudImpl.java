package com.wobangkj;

import com.wobangkj.api.Crud;
import com.wobangkj.api.Pageable;
import com.wobangkj.db.Sql;
import com.wobangkj.db.property.Column;
import com.wobangkj.utils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * crud
 *
 * @author @cliod
 * @since 5/30/20 3:51 PM
 * package: com.wobangkj
 */
public class CrudImpl implements Crud {

    private Class<?> type;
    private Column column;

    private JdbcTemplate template;

    private Sql query;
    private Sql update;
    private Sql insert;
    private Sql delete;

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
                for (String col : column.getColumns().keySet()) {
                    BeanUtils.setFieldValue(obj, col, rs.getString(col));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return obj;
        });
    }
}
