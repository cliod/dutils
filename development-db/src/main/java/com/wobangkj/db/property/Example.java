package com.wobangkj.db.property;

import com.wobangkj.api.Parser;

/**
 * sql condition
 *
 * @author @cliod
 * @since 4/11/20 4:08 PM
 * package: com.wobangkj.db
 */
public class Example implements Parser {

    public Example and() {
        return null;
    }

    public Example or() {
        return null;
    }

    public Example greater() {
        return null;
    }

    public Example smaller() {
        return null;
    }

    public Example equal() {
        return this;
    }

    @Override
    public String parse() {
        return null;
    }
}
