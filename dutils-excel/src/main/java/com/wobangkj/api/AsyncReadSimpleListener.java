package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;

/**
 * Asynchronous data reading
 *
 * @author cliod
 * @since 9/25/20 1:23 PM
 */
public abstract class AsyncReadSimpleListener<T> extends AsyncReadListener<T> {

	protected transient T data;

	@Override
	public final void invoke(T data, AnalysisContext context) {
		if (!this.filter(data)) {
			return;
		}
		this.data = data;
		this.fork(this::process);
	}

	@Override
	public final void doAfterAllAnalysed(AnalysisContext context) {
		this.fork(this::finish);
	}

	/**
	 * 在添加之前进行筛选
	 *
	 * @param data 数据
	 */
	protected boolean filter(T data) {
		return true;
	}

	/**
	 * 处理逻辑
	 */
	public final void process() {
		// 处理
		this.doProcess();
	}

	/**
	 * 真正处理
	 */
	protected abstract void doProcess();

	/**
	 * 完成
	 */
	protected void finish() {
	}
}
