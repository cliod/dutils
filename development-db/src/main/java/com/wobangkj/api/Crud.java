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

    List<?> findAll(Object condition, Pageable pageable);
}
