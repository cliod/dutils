package com.wobangkj.bean;

import com.wobangkj.api.Maps;
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
@Deprecated
public class Result<T> extends Maps<String, Object> implements SessionSerializable {

	private static final long serialVersionUID = -1884640212713045469L;

	public Result(int initialCapacity) {
		super(initialCapacity);
	}

	public Result() {
		super();
	}

	public Result(Map<? extends String, ?> m) {
		super(m);
	}

	public static @NotNull Result<Object> of(String k, Object v) {
		Result<Object> map = new Result<>();
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
	public static @NotNull Result<Object> of(int code, String msg) {
		Result<Object> result = new Result<>();
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
	public static @NotNull <T> Result<T> of(int code, String msg, Object o) {
		Result<T> result = new Result<>();
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
	public static @NotNull Result<Object> of(int code, String msg, @NotNull Throwable o) {
		Result<Object> result = new Result<>();
		result.setCode(code);
		result.setMsg(msg);
		result.setErr(o.getMessage());
		return result;
	}

	@Override
	public Result<T> add(String k, Object v) {
		put(k, v);
		return this;
	}

	@Override
	public Maps<String, Object> set(String k, Object v) {
		if (!Objects.isNull(v) && !Objects.isNull(k)) {
			put(k, v);
		}
		return this;
	}

	@Override
	public Result<T> del(String k) {
		remove(k);
		return this;
	}

	@Override
	public Object rem(String k) {
		return remove(k);
	}

	@Override
	public Object pop(String id) {
		return remove(id);
	}

	/**
	 * 转成字符串
	 *
	 * @return 字符串
	 */
	@Override
	public @NotNull String toString() {
		return this.toJson();
	}

	public @NotNull Object readResolve() throws Exception {
		return this.getClass().getConstructor().newInstance();
	}

	/**
	 * 转成Map对象
	 *
	 * @return java.util.Map
	 * @see java.util.Map
	 */
	@Override
	public @NotNull Result<T> toObject() {
		return this;
	}

	public Integer getCode() {
		return (Integer) get("status");
	}

	public void setCode(Integer code) {
		put("status", code);
	}

	@Deprecated
	public @NotNull Boolean getState() {
		return true;
	}

	@Deprecated
	public void setState(Boolean state) {
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
