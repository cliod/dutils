package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * 存储操作
 *
 * @author cliod
 * @since 7/9/20 5:03 PM
 */
public class SyncSaveListener<T> extends SyncReadProcessListener<T> {

	static final int COUNT = 1000;
	/**
	 * 处理
	 */
	private final Consumer<List<T>> processor;

	protected SyncSaveListener(Consumer<List<T>> processor) {
		super(COUNT);
		this.processor = processor;
	}

	public static <T> @NotNull SyncSaveListener<T> of(Consumer<List<T>> processor) {
		return new SyncSaveListener<>(processor);
	}

	/**
	 * 最大存储数量
	 *
	 * @return 数量
	 */
	@Override
	protected int getMax() {
		return COUNT;
	}

	/**
	 * 真正处理
	 *
	 * @param context 参数
	 */
	@Override
	protected void doProcess(AnalysisContext context) {
		this.processor.accept(this.cache);
	}
}
