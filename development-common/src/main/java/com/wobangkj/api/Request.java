package com.wobangkj.api;

import com.alibaba.fastjson.JSON;
import com.wobangkj.bean.Require;

/**
 * 请求
 *
 * @author cliod
 * @date 2019/11/9
 * package : com.wobangkj.api
 */
public class Request {
    /**
     * 工具类私有构造函数
     */
    public Request() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取参数
     *
     * @param json  参数Json
     * @param clazz 参数类型
     * @param <T>   类型:{@code} java.lang.Class
     * @return 结果
     */
    public static <T> T getParams(String json, Class<T> clazz) {
        return getField(json, "params", clazz);
    }

    /**
     * 获取参数指定字段值
     *
     * @param json  参数Json
     * @param key   指定字段
     * @param clazz 返回类型
     * @param <T>   类型:{@code} java.lang.Class
     * @return 结果
     */
    public static <T> T getField(String json, String key, Class<T> clazz) {
        return JSON.parseObject(json).getObject(key, clazz);
    }

    /**
     * 将Json参数转化为对象
     *
     * @param json 字符串
     * @return 对象
     */
    public static Require<?> fromString(String json) {
        return JSON.parseObject(json, Require.class);
    }

}