package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wobangkj.ThreadExecutor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 复杂操作
 *
 * @author @cliod
 * @since 7/9/20 5:03 PM
 */
@Slf4j
public abstract class DataListener<T extends Model> extends AnalysisEventListener<T> {

	/**
	 * 每隔3000条存储数据库，然后清理list ，方便内存回收
	 */
	private static final int BATCH_COUNT = 3000;
	/**
	 * 暂时存储列表
	 */
	protected final List<Model> dataCache = new ArrayList<>();
	/**
	 * 每次数据唯一id
	 */
	@Setter
	protected String id;

	@Deprecated
	public void setUuid(String uuid) {
		this.id = uuid;
	}

	@Override
	public void invoke(T data, AnalysisContext context) {
		this.process(data, context);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		//将剩余的数据进行处理
		ThreadExecutor.fork.execute(() -> {
			process(context);
			dataCache.clear();
		});
	}

	protected void process(Model data, AnalysisContext context) {
		if (this.filter(data, context)) {
			return;
		}
		dataCache.add(data);
		// 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
		if (dataCache.size() >= BATCH_COUNT) {
			ThreadExecutor.fork.execute(() -> {
				process(context);
				// 处理完成清理缓存
				dataCache.clear();
			});
		}
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
		return false;
	}

	/**
	 * 获取成功标识
	 *
	 * @return 字符串
	 */
	protected String getFailedTag() {
		return "";
	}

	/**
	 * 获取excel头标识
	 *
	 * @return 字符串
	 */
	protected String getTitleTag() {
		return "";
	}

}
