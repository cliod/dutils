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

	protected final static transient int maxSize = 500;
	private transient boolean isInitialize;

	public SyncReadSimpleListener() {
		this(maxSize);
	}

	public SyncReadSimpleListener(int initCapacity) {
		super(initCapacity);
	}

	@Override
	public final void invoke(T data, AnalysisContext context) {
		if (!this.isInitialize) {
			this.initialize();
			this.isInitialize = true;
		}
		if (!this.filter(data)) {
			return;
		}
		this.cache.add(data);
		if (this.condition()) {
			// 满足条件, 提前处理
			this.process();
		}
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (this.cache.size() >= this.getMax()) {
			this.process();
		}
	}

	@Override
	public final void doAfterAllAnalysed(AnalysisContext context) {
		try {
			this.process();
		} finally {
			this.finish();
		}
	}

	/**
	 * 初始化
	 */
	protected void initialize() {
	}

	/**
	 * 在添加之前进行筛选
	 *
	 * @param data 是否满足添加，不满足去除
	 */
	protected boolean filter(T data) {
		return true;
	}

	/**
	 * 满足的条件
	 *
	 * @return 是否满足
	 */
	protected boolean condition() {
		return false;
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

	protected void finish() {
	}
}
