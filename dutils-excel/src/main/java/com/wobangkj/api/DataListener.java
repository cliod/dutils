package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 复杂操作
 *
 * @author @cliod
 * @since 7/9/20 5:03 PM
 */
@Slf4j
@Deprecated
public abstract class DataListener<T extends Model> extends SyncReadProcessListener<T> {
	/**
	 * 真正处理
	 *
	 * @param context 参数
	 */
	@Override
	protected void doProcess(AnalysisContext context) {

	}
}
