package com.wobangkj.api;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 存储操作
 *
 * @author cliod
 * @since 7/9/20 5:03 PM
 */
@Deprecated
public class SaveListener<T> extends AnalysisEventListener<T> {

    final long count = 1000;
    private final List<T> data = new ArrayList<>();
    /**
     * 处理
     */
    private final Consumer<List<T>> process;

    protected SaveListener(Consumer<List<T>> process) {
        this.process = process;
    }

    public static <T> @NotNull SaveListener<T> of(Consumer<List<T>> process) {
        return new SaveListener<>(process);
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        if (Objects.nonNull(data))
            this.data.add(data);
        if (this.data.size() >= count) {
            process.accept(this.data);
            this.data.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        process.accept(this.data);
    }
}
