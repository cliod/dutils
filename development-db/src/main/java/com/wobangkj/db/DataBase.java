package com.wobangkj.db;

import com.wobangkj.api.Parser;
import com.wobangkj.db.property.Column;
import com.wobangkj.db.property.Table;

import java.util.List;

/**
 * database operation
 *
 * @author @cliod
 * @since 4/11/20 4:13 PM
 * package: com.wobangkj.db
 */
public class DataBase implements Parser {

    private Table table;

    private List<Column> columns;

    private List<Table> linkTables;


    @Override
    public String parse() {
        return null;
    }
}
