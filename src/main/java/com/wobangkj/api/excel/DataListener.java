package com.wobangkj.api.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * listener
 *
 * @author @cliod
 * @since 5/21/20 10:00 AM
 * package: com.wobangkj.jzlw.utils.excel.write
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
    protected String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        add(data, context);
    }

    protected void add(Model data, AnalysisContext context) {
        this.filter(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataCache.size() >= BATCH_COUNT) {
            process();
            // 处理完成清理缓存
            dataCache.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //将剩余的数据进行处理
        process();
    }

    /**
     * 加上存储数据库
     */
    protected abstract void process();

    /**
     * 在添加之前进行筛选
     *
     * @param data 数据
     */
    protected void filter(Model data) {
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
