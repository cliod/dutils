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
public abstract class SyncReadSimpleListener<T> extends SyncReadListener<T> {

	protected static transient int maxSize = 500;

	public SyncReadSimpleListener() {
		super(maxSize);
	}

	public SyncReadSimpleListener(int initCapacity) {
		super(initCapacity);
	}

	@Override
	public final void invoke(T data, AnalysisContext context) {
		if (!this.filter(data)) {
			return;
		}
		this.cache.add(data);
		if (this.condition()) {
			// 满足条件, 提前处理
			this.process();
		}
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (this.cache.size() >= getMax()) {
			this.process();
		}
	}

	/**
	 * 满足的条件
	 *
	 * @return 是否满足
	 */
	private boolean condition() {
		return false;
	}

	@Override
	public final void doAfterAllAnalysed(AnalysisContext context) {
		this.process();
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
	 * 最大存储数量
	 *
	 * @return 数量
	 */
	protected int getMax() {
		return maxSize;
	}

	/**
	 * 处理逻辑
	 */
	public final void process() {
		// 之前
		this.before();
		// 处理
		this.doProcess();
		// 之后
		this.after();
	}

	/**
	 * 之前
	 */
	protected void before() {
	}

	/**
	 * 真正处理
	 */
	protected abstract void doProcess();

	/**
	 * 之后
	 */
	protected void after() {
		this.cache.clear();
	}
}
