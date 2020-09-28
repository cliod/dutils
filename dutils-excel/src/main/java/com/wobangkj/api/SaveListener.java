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
public final class SaveListener<T> extends SyncSaveListener<T> {
	protected SaveListener(Consumer<List<T>> process) {
		super(process);
	}

	public static <T> @NotNull SyncSaveListener<T> of(Consumer<List<T>> process) {
		return new SaveListener<>(process);
	}
}
