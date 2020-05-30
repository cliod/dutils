package com.wobangkj.db.property;

import com.wobangkj.api.Parser;

/**
 * sql condition
 *
 * @author @cliod
 * @since 4/11/20 4:08 PM
 * package: com.wobangkj.db
 */
public class Condition implements Parser {

    public Condition orderBy() {
        return this;
    }

    public Condition groupBy() {
        return this;
    }

    public Condition limit() {
        return this;
    }

    @Override
    public String parse() {

        return "";
    }
}
