package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 存储操作
 *
 * @author cliod
 * @since 7/9/20 5:03 PM
 */
public class SyncSaveListener<T> extends SyncReadListener<T> {

	final long count = 1000;
	/**
	 * 处理
	 */
	private final Consumer<List<T>> process;

	protected SyncSaveListener(Consumer<List<T>> process) {
		this.process = process;
	}

	public static <T> @NotNull SyncSaveListener<T> of(Consumer<List<T>> process) {
		return new SyncSaveListener<>(process);
	}

	@Override
	public void invoke(T data, AnalysisContext context) {
		if (Objects.nonNull(data)) {
			super.invoke(data, context);
		}
		if (this.data.size() >= count) {
			this.process.accept(this.data);
			this.data.clear();
		}
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		this.process.accept(this.data);
		super.doAfterAllAnalysed(context);
	}
}
