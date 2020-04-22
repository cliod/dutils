package com.wobangkj.db.property;

/**
 * sql condition
 *
 * @author @cliod
 * @since 4/11/20 4:08 PM
 * package: com.wobangkj.db
 */
public class Condition {

    public Condition ifNull(Object obj, Object value) {
        return this;
    }

    public Condition orderBy() {
        return this;
    }

    public Condition groupBy() {
        return this;
    }

    public Condition limit() {
        return this;
    }
}
