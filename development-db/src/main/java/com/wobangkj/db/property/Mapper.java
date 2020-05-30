package com.wobangkj.db.property;

import lombok.Getter;

import java.util.Map;

/**
 * mapper
 *
 * @author @cliod
 * @since 5/30/20 3:37 PM
 * package: com.wobangkj.db.property
 */
@Getter
public class Mapper {

    private Map<String, String> mapper;
    private transient Table table;

    public Table call() {
        return table;
    }

    boolean isEmpty() {
        return false;
    }

    boolean containsKey(String key) {
        return this.mapper.containsKey(key);
    }

    boolean containsValue(String value) {
        return this.mapper.containsValue(value);
    }

    String get(String key) {
        return this.mapper.get(key);
    }

    public Mapper put(String key, String value) {
        this.mapper.put(key, value);
        return this;
    }
}
