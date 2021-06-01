package com.wobangkj.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * 存储操作
 *
 * @author cliod
 * @since 7/9/20 5:03 PM
 */
public class SyncSaveListener<T> extends SyncReadSimpleListener<T> {

	/**
	 * 处理
	 */
	private final Consumer<List<T>> processor;

	protected SyncSaveListener(Consumer<List<T>> processor) {
		this.processor = processor;
	}

	@Deprecated
	public static <T> @NotNull SyncSaveListener<T> of(Consumer<List<T>> processor) {
		return process(processor);
	}

	/**
	 * 处理
	 *
	 * @param processor 处理器
	 * @param <T>       类型
	 * @return 结果
	 */
	public static <T> @NotNull SyncSaveListener<T> process(Consumer<List<T>> processor) {
		return new SyncSaveListener<>(processor);
	}

	/**
	 * 真正处理
	 */
	@Override
	protected void doProcess() {
		this.processor.accept(this.cache);
	}
}
