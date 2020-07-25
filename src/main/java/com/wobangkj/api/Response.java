package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.bean.Pager;
import com.wobangkj.bean.Res;
import com.wobangkj.enums.ResultEnum;
import org.jetbrains.annotations.NotNull;

import static com.wobangkj.bean.Res.of;

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

    public static Maps<String, Object> OK = ok();
    public static Maps<String, Object> ERR = err("未知异常");
    public static Maps<String, Object> DELETE = ok(200, ResultEnum.SUCCESS_DELETE.getMsg());
    public static Maps<String, Object> UPDATE = ok(200, ResultEnum.SUCCESS_EDIT.getMsg());
    public static Maps<String, Object> INSERT = ok(200, ResultEnum.SUCCESS_ADD.getMsg());

    /**
     * 接口封装响应
     *
     * @param code 状态码
     * @param msg  响应消息
     * @return 结果
     */
    protected static @NotNull Res ok(int code, String msg) {
        return of(code, msg);
    }

    /**
     * 无返回(请求成功)
     */
    public static @NotNull Res ok() {
        return ok(200, "请求成功");
    }

    /**
     * 对象返回
     *
     * @param value 对象
     */
    public static @NotNull Res ok(Object value) {
        return ok().add("data", value);
    }

    /**
     * 键值对返回
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public static @NotNull Res ok(String key, Object value) {
        return ok().add("data", Maps.of(key, value));
    }

    /**
     * 分页返回
     *
     * @param pager 分页结果
     * @param <T>   类型
     * @return 结果
     */
    public static @NotNull <T> Res ok(@NotNull Pager<T> pager) {
        return ok()
                .add("data", pager.getData())
                .add("pager", Maps
                        .of("client_page", (Object) pager.getClientPage())
                        .set("every_page", pager.getEveryPage())
                        .set("total_num", pager.getTotalNum()));
    }

    /**
     * 失败271返回
     *
     * @param msg 消息
     * @return 结果
     */
    public static @NotNull Res fail(String msg) {
        return ok(271, msg);
    }

    /**
     * 失败返回,携带系统错误信息
     */
    public static @NotNull Res fail(@NotNull EnumTextMsg re) {
        return ok(re.getCode(), re.getMsg());
    }

    /**
     * 已处理失败返回
     */
    public static @NotNull Res fail(String msg, @NotNull ResultEnum re) {
        return of(271, msg, re.toThrowable());
    }

    /**
     * 失败返回,携带系统错误信息
     */
    public static @NotNull Res fail(@NotNull EnumTextMsg re, @NotNull Throwable throwable) {
        return of(re.getCode(), re.getMsg(), throwable);
    }

    /**
     * 失败返回,携带系统错误信息
     */
    public static @NotNull Res fail(String msg, @NotNull Throwable throwable) {
        return of(271, msg, throwable);
    }

    /**
     * 报错返回
     *
     * @param msg 500消息
     * @return 结果
     */
    public static @NotNull Res err(String msg) {
        return ok(500, msg);
    }

    /**
     * 未知异常
     *
     * @return 结果
     */
    public static @NotNull Res err() {
        return of(500, "未知异常");
    }

    /**
     * 未知异常,携带信息
     *
     * @param throwable 异常
     * @return 结果
     */
    public static @NotNull Res err(Throwable throwable) {
        return of(500, "系统异常", throwable);
    }

    /**
     * 非默认返回信息返回
     */
    @Deprecated
    public static @NotNull Res ok(@NotNull EnumMsg re, Object o) {
        return ok(re.getCode(), re.getMsg(), o);
    }

    /**
     * 其他信息返回
     */
    @Deprecated
    public static @NotNull Res ok(int code, String msg, Object o) {
        return of(code, msg, o);
    }

    /**
     * 失败返回,携带系统错误信息
     */
    @Deprecated
    public static @NotNull Res fail(@NotNull EnumMsg re, @NotNull Throwable throwable) {
        return of(re.getCode(), re.getMsg(), throwable);
    }

    /**
     * 失败返回
     */
    @Deprecated
    public static @NotNull Res fail(@NotNull EnumMsg re) {
        return of(re.getCode(), re.getMsg(), re.toThrowable());
    }

    /**
     * 已处理失败返回
     */
    @Deprecated
    public static @NotNull Res fail(@NotNull EnumMsg re, @NotNull EnumMsg err) {
        return of(re.getCode(), re.getMsg(), err.toThrowable());
    }

    /**
     * 位置异常,携带信息
     */
    @Deprecated
    public static @NotNull Res err(@NotNull EnumMsg err) {
        return of(500, "未知错误", err.toThrowable());
    }

}