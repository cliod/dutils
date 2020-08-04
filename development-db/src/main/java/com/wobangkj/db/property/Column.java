package com.wobangkj.db.property;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * sql colnum mapper
 *
 * @author @cliod
 * @since 4/11/20 4:12 PM
 * package: com.wobangkj.db
 */
public class Column extends HashMap<String, String> implements Map<String, String> {

    private String key;
    private String value;

    public Column(String key, String value) {
        super(16);
        this.key = key;
        this.value = value;
    }

    public Column(int initialCapacity, String key, String value) {
        super(initialCapacity);
        this.key = key;
        this.value = value;
        this.put(key, value);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return super.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        super.put(value, key);
        return super.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return super.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (Entry<? extends String, ? extends String> entry : m.entrySet()) {
            this.put(entry.getValue(), entry.getKey());
        }
        super.putAll(m);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return super.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return super.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return super.entrySet();
    }
}
