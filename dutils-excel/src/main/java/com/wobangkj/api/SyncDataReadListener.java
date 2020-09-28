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
public abstract class SyncDataReadListener<T extends Model> extends SyncReadListener<T> {

	/**
	 * 每隔3000条存储数据库，然后清理list ，方便内存回收
	 */
	private static final int BATCH_COUNT = 500;

	@Override
	public void invoke(T data, AnalysisContext context) {
		this.data.add(data);
		if (!this.filter(data, context)) {
			return;
		}
		this.data.add(data);
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (this.data.size() >= BATCH_COUNT) {
			this.process(context);
			// 处理完成清理缓存
			this.data.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		this.process(context);
		this.data.clear();
	}

	/**
	 * 加上存储数据库
	 *
	 * @param context 上下文内容
	 */
	protected abstract void process(AnalysisContext context);

	/**
	 * 在添加之前进行筛选
	 *
	 * @param data    数据
	 * @param context 上下文内容
	 */
	protected boolean filter(Model data, AnalysisContext context) {
		return true;
	}
}
