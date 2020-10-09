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
public abstract class SyncReadProcessListener<T> extends SyncReadListener<T> {

	@Override
	public final void invoke(T data, AnalysisContext context) {
		if (!this.filter(data, context)) {
			return;
		}
		this.cache.add(data);
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (this.cache.size() >= getMax()) {
			this.process(context);
		}
	}

	@Override
	public final void doAfterAllAnalysed(AnalysisContext context) {
		this.process(context);
	}

	/**
	 * 在添加之前进行筛选
	 *
	 * @param data    数据
	 * @param context 上下文内容
	 */
	protected boolean filter(T data, AnalysisContext context) {
		return true;
	}

	/**
	 * 最大存储数量
	 *
	 * @return 数量
	 */
	protected int getMax() {
		return 500;
	}

	/**
	 * 处理逻辑
	 */
	public final void process(AnalysisContext context) {
		// 之前
		this.before(context);
		// 处理
		this.doProcess(context);
		// 之后
		this.after(context);
	}

	/**
	 * 之前
	 *
	 * @param context 参数
	 */
	protected void before(AnalysisContext context) {
	}

	/**
	 * 真正处理
	 *
	 * @param context 参数
	 */
	protected abstract void doProcess(AnalysisContext context);

	/**
	 * 之后
	 *
	 * @param context 参数
	 */
	protected void after(AnalysisContext context) {
		this.cache.clear();
	}
}
