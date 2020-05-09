package com.wobangkj.api;

import com.wobangkj.bean.Page;
import com.wobangkj.bean.Result;
import com.wobangkj.enums.ResultEnum;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wobangkj.bean.Result.of;

/**
 * 接口数据响应包装
 *
 * @author cliod
 * @since 2019/9/14
 * package : com.wobangkj.bean
 */
public class Response {
    /**
     * 工具类私有构造函数
     */
    public Response() {
        throw new UnsupportedOperationException();
    }

    public static Result<Object> SUCCESS = ok();
    public static Result<Object> UPDATE = ok(ResultEnum.SUCCESS_EDIT, null);
    public static Result<Object> INSERT = ok(ResultEnum.SUCCESS_ADD, null);
    public static Result<Object> DELETE = ok(ResultEnum.SUCCESS_DELETE, null);

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
        return ok(new HashMap<String, Object>(4) {{
            put(valueName, value);
        }});
    }

    /**
     * 多自定义字段, value 返回
     */
    @SafeVarargs
    @Deprecated
    @NotNull
    public static <V> Result<Map<String, Object>> ok(@NotNull String[] valueNames, @NotNull V... values) {
        int len = Math.min(valueNames.length, values.length);
        if (len == 0) {
            return ok(new HashMap<>(0));
        } else {
            Map<String, Object> map = new HashMap<>(len * 4 / 3 + 1);
            for (int i = 0; i < len; i++) {
                map.put(valueNames[i], values[i]);
            }
            return ok(map);
        }
    }

    @SafeVarargs
    @Deprecated
    @NotNull
    public static <V> Result<Map<String, Object>> ok(@NotNull String titles, V... values) {
        return ok(titles.split(","), values);
    }

    /**
     * 非默认返回信息返回
     */
    @NotNull
    public static <T> Result<T> ok(@NotNull EnumMsg re, T o) {
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
    public static Result<Object> error(@NotNull EnumMsg err) {
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
    public static Result<Object> fail(@NotNull ResultEnum re, @NotNull Throwable err) {
        return of(re.getCode(), false, re.getMsg(), err.getMessage(), null);
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
    public static Result<Object> fail(@NotNull ResultEnum re, @NotNull EnumMsg err) {
        return of(re.getCode(), false, re.getMsg(), err.toObject(), null);
    }

    /**
     * 已处理失败返回
     */
    @NotNull
    public static Result<Object> fail(@NotNull ResultEnum re, int code, String msg) {
        return of(re.getCode(), false, re.getMsg(), new HashMap<String, Object>(16) {{
            put("code", code);
            put("msg", msg);
        }}, null);
    }

    @NotNull
    public static Builder build(String title, Object data) {
        Builder builder = Builder.build();
        builder.put(title, data);
        return builder;
    }

    @NotNull
    public static Builder build() {
        return Builder.build();
    }

    /**
     * build模式
     */
    @Data
    public static class Builder {
        private Map<String, Object> data;

        Builder() {
            this.data = new HashMap<>(16);
        }

        @NotNull
        public Builder put(String title, Object data) {
            this.data.put(title, data);
            return this;
        }

        static @NotNull Builder build() {
            return new Builder();
        }

        @NotNull
        public Builder add(String title, Object data) {
            this.data.put(title, data);
            return this;
        }

        @NotNull
        public Result<?> ok() {
            return Response.ok(this.data);
        }

        public void set(Map<String, Object> data) {
            this.data = data;
        }
    }
}