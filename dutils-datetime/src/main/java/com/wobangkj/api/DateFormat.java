package com.wobangkj.api;

/**
 * 获取样式
 *
 * @author cliod
 * @since 7/8/20 5:02 PM
 */
@FunctionalInterface
public interface DateFormat extends Format<String> {
    /**
     * 获取样式
     *
     * @return 样式
     */
    @Override
    String getPattern();
}
