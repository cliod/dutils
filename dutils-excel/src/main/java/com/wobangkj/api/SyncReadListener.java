package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Synchronous data reading
 *
 * @author cliod
 * @since 9/25/20 1:23 PM
 */
public abstract class SyncReadListener<T> extends AnalysisEventListener<T> {

	protected List<T> data = new ArrayList<>();

	/**
	 * When analysis one row trigger invoke function.
	 *
	 * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
	 * @param context A context is the main anchorage point of a excel reader.
	 */
	@Override
	public void invoke(T data, AnalysisContext context) {
		this.data.add(data);
	}

	/**
	 * if have something to do after all analysis
	 *
	 * @param context A context is the main anchorage point of a excel reader.
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {

	}
}
