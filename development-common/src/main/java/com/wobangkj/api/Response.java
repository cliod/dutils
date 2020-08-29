package com.wobangkj.api;

import com.wobangkj.bean.Page;
import com.wobangkj.bean.Res;
import com.wobangkj.bean.Result;
import com.wobangkj.enums.ResultEnum;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, Object> OK = ok();
    public static Map<String, Object> ERR = err("未知异常");
    public static Map<String, Object> DELETE = ok(200, ResultEnum.SUCCESS_DELETE.getMsg());
    public static Maps<String, Object> UPDATE = ok(200, ResultEnum.SUCCESS_EDIT.getMsg());
    public static Maps<String, Object> INSERT = ok(200, ResultEnum.SUCCESS_ADD.getMsg());

    public static Page<Object> PAGE_NULL = Page.of();

    /**
     * 接口封装响应
     *
     * @param code 状态码
     * @param msg  响应消息
     * @return 结果
     */
    protected static @NotNull Res ok(int code, String msg) {
        return Res.ofRes(code, msg);
    }

    /**
     * 无返回(请求成功)
     */
    public static @NotNull Maps<String, Object> ok() {
        return ok(200, "请求成功");
    }

    /**
     * 对象返回
     *
     * @param value 对象
     */
    public static @NotNull Maps<String, Object> ok(Object value) {
        return ok().add("data", value);
    }

    /**
     * 键值对返回
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public static @NotNull Maps<String, Object> ok(String key, Object value) {
        return ok().add("data", Maps.of(key, value));
    }

    /**
     * 分页返回
     *
     * @param pager 分页结果
     * @param <T>   类型
     * @return 结果
     */
    public static @NotNull <T> Maps<String, Object> ok(@NotNull Page<T> pager) {
        return ok()
                .add("data", pager.getList())
                .add("pager", Maps
                        .of("client_page", (Object) pager.getPage())
                        .set("every_page", pager.getSize())
                        .set("total_num", pager.getCount()));
    }

    /**
     * 失败271返回
     *
     * @param msg 消息
     * @return 结果
     */
    public static @NotNull Maps<String, Object> fail(String msg) {
        return ok(271, msg);
    }

    /**
     * 报错返回
     *
     * @param msg 500消息
     * @return 结果
     */
    public static @NotNull Maps<String, Object> err(String msg) {
        return ok(500, msg);
    }

    /**
     * 未知异常
     *
     * @return 结果
     */
    public static @NotNull Res error() {
        return Res.ofRes(500, "未知异常");
    }

    /**
     * 未知异常,携带信息
     *
     * @param throwable 异常
     * @return 结果
     */
    public static @NotNull Res error(Throwable throwable) {
        return Res.ofRes(500, "系统异常", throwable);
    }

    /**
     * 已处理失败返回
     */
    public static @NotNull Res fail(String msg, @NotNull ResultEnum re) {
        return Res.ofRes(271, msg, re.toThrowable());
    }

    /**
     * 多自定义字段, value 返回
     */
    @SafeVarargs
    @Deprecated
    @NotNull
    public static <V> Maps<String, Object> ok(@NotNull String[] valueNames, @NotNull V... values) {
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
    public static <V> Maps<String, Object> ok(@NotNull String titles, V... values) {
        return ok(titles.split(","), values);
    }

    /**
     * 非默认返回信息返回
     */
    @NotNull
    @Deprecated
    public static <T> Res ok(@NotNull EnumMsg re, T o) {
        return ok(re.getCode(), re.getMsg(), o);
    }

    /**
     * 其他信息返回
     */
    @NotNull
    @Deprecated
    public static <T> Res ok(int code, String msg, T o) {
        return Res.ofRes(code, msg, o);
    }

    /**
     * 位置异常,携带信息
     */
    @NotNull
    @Deprecated
    public static Result<Object> error(@NotNull EnumMsg err) {
        return Res.ofRes(500, "未知错误", err.toThrowable());
    }

    /**
     * 失败返回,携带系统错误信息
     */
    @NotNull
    @Deprecated
    public static Result<Object> fail(@NotNull ResultEnum re, @NotNull Throwable throwable) {
        return Res.ofRes(re.getCode(), re.getMsg(), throwable);
    }

    /**
     * 失败返回
     */
    @NotNull
    @Deprecated
    public static Result<Object> fail(@NotNull ResultEnum re) {
        return Res.ofRes(re.getCode(), re.getMsg(), re.toThrowable());
    }

    /**
     * 已处理失败返回
     */
    @NotNull
    @Deprecated
    public static Result<Object> fail(@NotNull ResultEnum re, @NotNull EnumMsg err) {
        return Res.ofRes(re.getCode(), re.getMsg(), err.toThrowable());
    }

    @NotNull
    @Deprecated
    public static Builder build(String title, Object data) {
        Builder builder = Builder.build();
        builder.put(title, data);
        return builder;
    }

    @NotNull
    @Deprecated
    public static Builder build() {
        return Builder.build();
    }

    /**
     * build模式
     */
    @Data
    @Deprecated
    public static class Builder {
        private Map<String, Object> data;

        Builder() {
            this.data = new HashMap<>(16);
        }

        @Deprecated
        static @NotNull Builder build() {
            return new Builder();
        }

        @Deprecated
        public @NotNull Builder put(String title, Object data) {
            this.data.put(title, data);
            return this;
        }

        @Deprecated
        public @NotNull Builder add(String title, Object data) {
            this.data.put(title, data);
            return this;
        }

        @NotNull
        @Deprecated
        public Maps<String, Object> ok() {
            return Response.ok(this.data);
        }

        @Deprecated
        public void set(Map<String, Object> data) {
            this.data = data;
        }
    }
}