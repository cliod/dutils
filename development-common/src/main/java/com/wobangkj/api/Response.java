package com.wobangkj.api;

import com.alibaba.fastjson.JSON;
import com.wobangkj.bean.Page;
import com.wobangkj.bean.Result;
import com.wobangkj.enums.ResultEnum;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wobangkj.bean.Result.of;

/**
 * 接口数据响应包装
 *
 * @author cliod
 * @date 2019/9/14
 * package : com.wobangkj.bean
 */
public class Response {
    /**
     * 工具类私有构造函数
     */
    public Response() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获data字段的值
     *
     * @param json  Json
     * @param clazz 返回值的对象
     * @param <T>   类型:{@code} java.lang.Class
     * @return 结果
     */
    public static <T> T getData(@NotNull String json, @NotNull Class<T> clazz) {
        return getField(json, "data", clazz);
    }

    /**
     * 获data字段的值
     *
     * @param json Json
     * @return 结果
     */
    public static Object getData(@NotNull String json) {
        return getField(json, "data");
    }

    /**
     * 获其中一个字段的值
     *
     * @param json  Json
     * @param key   字段
     * @param clazz 返回值的对象
     * @param <T>   类型:{@code} java.lang.Class
     * @return 结果
     */
    public static <T> T getField(@NotNull String json, @NotNull String key, @NotNull Class<T> clazz) {
        return JSON.parseObject(json).getObject(key, clazz);
    }

    /**
     * 获其中一个字段的值
     *
     * @param json Json
     * @param key  字段
     * @return 结果
     */
    public static Object getField(@NotNull String json, @NotNull String key) {
        return JSON.parseObject(json).getJSONObject(key);
    }

    /**
     * 无返回
     */
    @NotNull
    public static Result<Object> ok() {
        return ok(null);
    }

    /**
     * 对象返回
     */
    @NotNull
    public static <T> Result<T> ok(T o) {
        return ok(ResultEnum.SUCCESS, o);
    }

    /**
     * 分页返回
     */
    @NotNull
    public static <T> Result<Page<T>> ok(long length, List<T> list) {
        return of(200, true, "successful", Page.of(length, list));
    }

    /**
     * 自定义字段, value 返回
     */
    @NotNull
    public static <V> Result<Map<String, Object>> ok(String valueName, V value) {
        return ok(new HashMap<>(16) {{
            put(valueName, value);
        }});
    }

    /**
     * 多自定义字段, value 返回
     */
    @SafeVarargs
    @NotNull
    public static <V> Result<Map<String, Object>> ok(@NotNull String[] valueNames, @NotNull V... values) {
        int len = valueNames.length;
        if (len == 0) {
            return ok(new HashMap<>(4));
        } else {
            Map<String, Object> map = new HashMap<>(len * 4 / 3 + 1);
            for (int i = 0; i < len; i++) {
                map.put(valueNames[i], values[i]);
            }
            return ok(map);
        }
    }

    /**
     * 非默认返回信息返回
     */
    @NotNull
    public static <T> Result<T> ok(@NotNull ResultEnum re, T o) {
        return ok(re.getCode(), re.getMsg(), o);
    }

    /**
     * 其他信息返回
     */
    @NotNull
    public static <T> Result<T> ok(int code, String msg, T o) {
        return of(code, true, msg, o);
    }

    /**
     * 未知异常
     */
    @NotNull
    public static Result<Object> error() {
        return of(500, false, "系统错误", null);
    }

    /**
     * 位置异常,携带信息
     */
    @NotNull
    public static Result<Object> error(@NotNull BaseEnum<? extends Enum<?>> err) {
        return of(500, false, "未知错误", err.toObject(), null);
    }

    /**
     * 未知异常,携带信息
     */
    @NotNull
    public static Result<Object> error(Throwable msg) {
        return of(500, false, "未知错误", msg, null);
    }

    /**
     * 失败返回,携带系统错误信息
     */
    @NotNull
    public static Result<Object> fail(@NotNull ResultEnum re, Throwable err) {
        return of(re.getCode(), false, re.getMsg(), err, null);
    }

    /**
     * 失败返回
     */
    @NotNull
    public static Result<Object> fail(@NotNull ResultEnum re) {
        return of(re.getCode(), false, re.getMsg(), re.toObject(), null);
    }

    /**
     * 已处理失败返回
     */
    @NotNull
    public static Result<Object> fail(@NotNull ResultEnum re, @NotNull BaseEnum<? extends Enum<?>> err) {
        return of(re.getCode(), false, re.getMsg(), err.toObject(), null);
    }

    /**
     * 已处理失败返回
     */
    @NotNull
    public static Result<Object> fail(@NotNull ResultEnum re, int code, String msg) {
        return of(re.getCode(), false, re.getMsg(), new HashMap<String, Object>(16) {{
            put("code", code);
            put("errMsg", msg);
        }}, null);
    }
}