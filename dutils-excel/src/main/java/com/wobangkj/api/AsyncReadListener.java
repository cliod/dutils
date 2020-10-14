package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wobangkj.ThreadExecutor;

/**
 * Asynchronous data reading
 *
 * @author cliod
 * @since 9/25/20 1:23 PM
 */
public abstract class AsyncReadListener<T> extends AnalysisEventListener<T> {

	/**
	 * When analysis one row trigger invoke function.
	 *
	 * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
	 * @param context A context is the main anchorage point of a excel reader.
	 */
	@Override
	public void invoke(final T data, AnalysisContext context) {
	}

	/**
	 * if have something to do after all analysis
	 *
	 * @param context A context is the main anchorage point of a excel reader.
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
	}

	/**
	 * 多线程处理
	 *
	 * @param r 参数
	 */
	protected final void fork(Runnable r) {
		ThreadExecutor.FORK.execute(r);
	}
}
