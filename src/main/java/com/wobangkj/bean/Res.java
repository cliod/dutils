package com.wobangkj.bean;

import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * api返回统一封装
 *
 * @author cliod
 * @since 2019/5/20 13:20
 */
public class Res extends Maps<String, Object> implements SessionSerializable {

	private static final long serialVersionUID = -4892758069561565904L;

	public Res(int initialCapacity) {
		super(initialCapacity);
	}

	public Res() {
		super();
	}

	public Res(Map<? extends String, ?> m) {
		super(m);
	}

	public static @NotNull Res of(String k, Object v) {
		Res map = new Res();
		map.put(k, v);
		return map;
	}

	/**
	 * 代替构造方法
	 *
	 * @param code 状态码
	 * @param msg  响应消息
	 * @return result
	 */
	@Deprecated
	public static @NotNull Res of(int code, String msg) {
		Res result = new Res();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	/**
	 * 代替构造方法
	 *
	 * @param code 状态码
	 * @param msg  响应消息
	 * @param o    响应对象
	 * @return result
	 */
	@Deprecated
	public static @NotNull Res of(int code, String msg, Object o) {
		Res result = new Res();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(o);
		return result;
	}

	/**
	 * 代替构造方法
	 *
	 * @param code 状态码
	 * @param msg  响应消息
	 * @param o    响应异常
	 * @return result
	 */
	@Deprecated
	public static @NotNull Res of(int code, String msg, @NotNull Throwable o) {
		Res result = new Res();
		result.setCode(code);
		result.setMsg(msg);
		result.setErr(o.getMessage());
		return result;
	}

	/**
	 * 代替构造方法
	 *
	 * @param code 状态码
	 * @param msg  响应消息
	 * @return result
	 */
	public static @NotNull Res ofRes(int code, String msg) {
		Res result = new Res();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}

	/**
	 * 代替构造方法
	 *
	 * @param code 状态码
	 * @param msg  响应消息
	 * @param o    响应对象
	 * @return result
	 */
	public static @NotNull Res ofRes(int code, String msg, Object o) {
		Res result = new Res();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(o);
		return result;
	}

	/**
	 * 代替构造方法
	 *
	 * @param code 状态码
	 * @param msg  响应消息
	 * @param o    响应异常
	 * @return result
	 */
	public static @NotNull Res ofRes(int code, String msg, @NotNull Throwable o) {
		Res result = new Res();
		result.setCode(code);
		result.setMsg(msg);
		result.setErr(o.getMessage());
		return result;
	}

	public Res addAll(Map<String, Object> map) {
		putAll(map);
		return this;
	}

	@Override
	public Res add(String k, Object v) {
		put(k, v);
		return this;
	}

	@Override
	public Res set(String k, Object v) {
		if (!Objects.isNull(v) && !Objects.isNull(k)) {
			put(k, v);
		}
		return this;
	}

	@Override
	public Res del(String k) {
		remove(k);
		return this;
	}

	@Override
	public Object rem(String s) {
		return super.rem(s);
	}

	@Override
	public Object pop(String s) {
		return super.pop(s);
	}

	/**
	 * 转成Map对象
	 *
	 * @return java.util.Map
	 * @see Map
	 */
	@Override
	public @NotNull Res toObject() {
		return this;
	}

	/**
	 * 转成字符串
	 *
	 * @return 字符串
	 */
	@Override
	public final @NotNull String toString() {
		return this.toJson();
	}

	public final @NotNull Object readResolve() throws Exception {
		return this.getClass().getConstructor().newInstance();
	}

	public Integer getCode() {
		return (Integer) get("status");
	}

	public void setCode(Integer code) {
		put("status", code);
	}

	public Integer getStatus() {
		return (Integer) get("status");
	}

	public void setStatus(Integer code) {
		put("status", code);
	}

	public String getMsg() {
		return (String) get("msg");
	}

	public void setMsg(String msg) {
		put("msg", msg);
	}

	public Object getErr() {
		return get("err");
	}

	public void setErr(Object err) {
		put("err", err);
	}

	public Object getData() {
		return get("data");
	}

	public void setData(Object data) {
		put("data", data);
	}

	public <E> E getData(Class<E> type) {
		return JsonUtils.fromJson(JsonUtils.toJson(get("data")), type);
	}
}
