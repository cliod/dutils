package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.bean.Pager;
import com.wobangkj.bean.Res;
import com.wobangkj.enums.ResultEnum;
import org.jetbrains.annotations.NotNull;

import static com.wobangkj.bean.Res.of;
import static com.wobangkj.bean.Res.ofRes;

/**
 * 接口数据响应包装
 *
 * @author cliod
 * @since 2019/9/14
 * package : com.wobangkj.bean
 */
public interface Response {
    public static final int showMsg = 271;
    public static final int successMsg = 200;
    public static final int errorMsg = 500;
    public static Maps<String, Object> OK = ok();
    public static Maps<String, Object> ERR = err("未知异常");
    public static Maps<String, Object> DELETE = ofRes(successMsg, ResultEnum.SUCCESS_DELETE.getMsg());
    public static Maps<String, Object> UPDATE = ofRes(successMsg, ResultEnum.SUCCESS_EDIT.getMsg());
    public static Maps<String, Object> INSERT = ofRes(successMsg, ResultEnum.SUCCESS_ADD.getMsg());

    /**
     * 无返回(请求成功)
     */
    public static @NotNull Res ok() {
        return ofRes(successMsg, "请求成功");
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
        return ok(Maps.of(key, value));
    }

    /**
     * 分页返回
     *
     * @param pager 分页结果
     * @param <T>   类型
     * @return 结果
     */
    public static @NotNull <T> Res ok(@NotNull Pager<T> pager) {
        return ok(pager.getData())
                .add("pager", of("client_page", pager.getClientPage())
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
        return ofRes(showMsg, msg);
    }

    /**
     * 已处理失败返回
     */
    public static @NotNull Res fail(String msg, @NotNull EnumMsg em) {
        return ofRes(showMsg, msg, em.toThrowable());
    }

    /**
     * 失败返回,携带系统错误信息
     */
    public static @NotNull Res fail(String msg, @NotNull Throwable throwable) {
        return ofRes(showMsg, msg, throwable);
    }

    /**
     * 失败返回,携带系统错误信息
     */
    public static @NotNull Res fail(@NotNull EnumTextMsg msg) {
        return ofRes(msg.getCode(), msg.getMsg());
    }

    /**
     * 失败返回,携带系统错误信息
     */
    public static @NotNull Res fail(@NotNull EnumTextMsg msg, @NotNull EnumMsg em) {
        return ofRes(msg.getCode(), msg.getMsg(), em.toThrowable());
    }

    /**
     * 失败返回,携带系统错误信息
     */
    public static @NotNull Res fail(@NotNull EnumTextMsg msg, @NotNull Throwable throwable) {
        return ofRes(msg.getCode(), msg.getMsg(), throwable);
    }

    /**
     * 未知异常
     *
     * @return 结果
     */
    public static @NotNull Res err() {
        return ofRes(errorMsg, "未知异常");
    }

    /**
     * 报错返回
     *
     * @param msg 500消息
     * @return 结果
     */
    public static @NotNull Res err(String msg) {
        return ofRes(errorMsg, msg);
    }

    /**
     * 未知异常,携带信息
     *
     * @param throwable 异常
     * @return 结果
     */
    public static @NotNull Res err(Throwable throwable) {
        return ofRes(errorMsg, "系统异常", throwable);
    }

    /**
     * 非默认返回信息返回
     */
    @Deprecated
    public static @NotNull Res ok(@NotNull EnumMsg msg, Object o) {
        return ok(msg.getCode(), msg.getMsg(), o);
    }

    /**
     * 其他信息返回
     */
    @Deprecated
    public static @NotNull Res ok(int code, String msg, Object o) {
        return ofRes(code, msg, o);
    }

    /**
     * 失败返回
     */
    @Deprecated
    public static @NotNull Res fail(@NotNull EnumMsg msg) {
        return ofRes(msg.getCode(), msg.getMsg(), msg.toThrowable());
    }

    /**
     * 失败返回,携带系统错误信息
     */
    @Deprecated
    public static @NotNull Res fail(@NotNull EnumMsg msg, @NotNull Throwable throwable) {
        return ofRes(msg.getCode(), msg.getMsg(), throwable);
    }

    /**
     * 已处理失败返回
     */
    @Deprecated
    public static @NotNull Res fail(@NotNull EnumMsg msg, @NotNull EnumMsg em) {
        return ofRes(msg.getCode(), msg.getMsg(), em.toThrowable());
    }

    /**
     * 位置异常,携带信息
     */
    @Deprecated
    public static @NotNull Res err(@NotNull EnumMsg err) {
        return ofRes(errorMsg, "未知错误", err.toThrowable());
    }

}