package com.wobangkj.api;

import java.util.List;

/**
 * crud
 *
 * @author @cliod
 * @since 5/30/20 1:39 PM
 * package: com.example.demo.db.api
 */
public interface Crud {

    Object findById(Object id);

    Object findOne(Object obj);

    List<?> findAll(Object condition, Pageable pageable);

    Object insert(Object obj);

    Object update(Object obj);

    int deleteById(Object id);
}
