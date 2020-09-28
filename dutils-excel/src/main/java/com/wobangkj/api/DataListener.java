package com.wobangkj.api;

import lombok.extern.slf4j.Slf4j;

/**
 * 复杂操作
 *
 * @author @cliod
 * @since 7/9/20 5:03 PM
 */
@Slf4j
@Deprecated
public abstract class DataListener<T extends Model> extends SyncDataReadListener<T> {
}
