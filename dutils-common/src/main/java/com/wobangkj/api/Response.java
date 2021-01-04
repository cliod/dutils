package com.wobangkj.api;

import com.wobangkj.bean.Maps;
import com.wobangkj.bean.Pager;
import com.wobangkj.bean.Res;
import org.jetbrains.annotations.NotNull;

import static com.wobangkj.bean.Res.ofRes;

/**
 * 接口数据响应包装
 *
 * @author cliod
 * @since 2019/9/14
 * package : com.wobangkj.bean
 */
public final class Response {
	public static final int SHOW_CODE = 271;
	public static final int SUCCESS_CODE = 200;
	public static final int ERROR_CODE = 500;
	public static Res OK = ofRes(SUCCESS_CODE, "请求成功");
	public static Res ERR = ofRes(ERROR_CODE, "未知异常");
	public static Res DELETE = ofRes(SUCCESS_CODE, "删除成功");
	public static Res UPDATE = ofRes(SUCCESS_CODE, "修改成功");
	public static Res INSERT = ofRes(SUCCESS_CODE, "创建成功");

	/**
	 * 无返回(请求成功)
	 *
	 * @return 成功空对象
	 */
	public static @NotNull Res ok() {
		return ofRes(SUCCESS_CODE, "请求成功");
	}

	/**
	 * 对象返回
	 *
	 * @param value 对象
	 * @return 成功对象
	 */
	public static @NotNull Res ok(Object value) {
		return ok().add("data", value);
	}

	/**
	 * 键值对返回
	 *
	 * @param key   键
	 * @param value 值
	 * @return 成功对象
	 */
	public static @NotNull Res ok(String key, Object value) {
		return ok().add("data", Maps.of(key, value));
	}

	/**
	 * 从Maps的对象
	 *
	 * @param res 原对象
	 * @return 成功对象
	 */
	public static @NotNull Res ok(Maps<String, Object> res) {
		return Res.from(res).addAll(OK);
	}

	/**
	 * 分页返回
	 *
	 * @param pager 分页结果
	 * @param <T>   类型
	 * @return 结果
	 */
	public static @NotNull <T> Res ok(@NotNull Pager<T> pager) {
		return ofRes(SUCCESS_CODE, "请求成功", pager);
	}

	/**
	 * 失败271返回
	 *
	 * @param msg 消息
	 * @return 结果
	 */
	public static @NotNull Res fail(String msg) {
		return ofRes(SHOW_CODE, msg);
	}

	/**
	 * 已处理失败返回
	 *
	 * @param msg 消息
	 * @param em  消息
	 * @return 失败对象
	 */
	public static @NotNull Res fail(String msg, @NotNull EnumMsg em) {
		return ofRes(SHOW_CODE, msg, em.toThrowable());
	}

	/**
	 * 失败返回,携带系统错误信息
	 *
	 * @param msg       消息
	 * @param throwable 可抛出消息
	 * @return 失败对象
	 */
	public static @NotNull Res fail(String msg, @NotNull Throwable throwable) {
		return ofRes(SHOW_CODE, msg, throwable);
	}

	/**
	 * 失败返回,携带系统错误信息
	 *
	 * @param msg 消息
	 * @return 失败对象
	 */
	public static @NotNull Res fail(@NotNull EnumTextMsg msg) {
		return ofRes(msg.getCode(), msg.getMsg());
	}

	/**
	 * 失败返回,携带系统错误信息
	 *
	 * @param msg 消息
	 * @param em  消息
	 * @return 失败对象
	 */
	public static @NotNull Res fail(@NotNull EnumTextMsg msg, @NotNull EnumMsg em) {
		return ofRes(msg.getCode(), msg.getMsg(), em.toThrowable());
	}

	/**
	 * 失败返回,携带系统错误信息
	 *
	 * @param msg       消息
	 * @param throwable 可抛出消息
	 * @return 失败对象
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
		return ERR;
	}

	/**
	 * 报错返回
	 *
	 * @param msg 500消息
	 * @return 结果
	 */
	public static @NotNull Res err(String msg) {
		return ofRes(ERROR_CODE, msg);
	}

	/**
	 * 报错返回
	 *
	 * @param msg 500消息
	 * @return 结果
	 */
	public static @NotNull Res err(EnumMsg msg) {
		return ofRes(ERROR_CODE, msg.getMsg());
	}

	/**
	 * 未知异常,携带信息
	 *
	 * @param throwable 异常
	 * @return 结果
	 */
	public static @NotNull Res err(Throwable throwable) {
		return ofRes(ERROR_CODE, "系统异常", throwable);
	}
}