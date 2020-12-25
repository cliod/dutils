package com.wobangkj.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wobangkj.api.IRes;
import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.JsonUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 旧版结果封装
 *
 * @author cliod
 * @since 19-6-9
 */
@Data
@Deprecated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements IRes, SessionSerializable {
	private static final long serialVersionUID = -1884640212713045469L;
	private Integer code;
	private String msg;
	private Object err;
	private T data;

	/**
	 * 代替构造方法
	 *
	 * @param code code
	 * @param msg  msg
	 * @param err  异常详细信息
	 * @param data T
	 * @param <T>  类型
	 * @return result
	 */
	public static <T> @NotNull Result<T> of(Integer code, String msg, Object err, T data) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		result.setErr(err);
		return result;
	}

	/**
	 * 代替构造方法
	 *
	 * @param code code
	 * @param msg  msg
	 * @param data T
	 * @return result
	 */
	public static @NotNull <T> Result<T> of(Integer code, String msg, T data) {
		return of(code, msg, null, data);
	}

	/**
	 * 代替构造方法
	 *
	 * @param code code
	 * @param msg  msg
	 * @return result
	 */
	public static @NotNull <T> Result<T> of(Integer code, String msg) {
		return of(code, msg, null);
	}

	/**
	 * 从res转化
	 *
	 * @param res res
	 * @return result
	 */
	public static @NotNull <T> Result<T> ofRes(Res res) {
		@SuppressWarnings("unchecked")
		T t = (T) res.getData();
		return of(res.getStatus(), res.getMsg(), res.getErr(), t);
	}

	/**
	 * 转成res对象
	 *
	 * @return res
	 */
	@Override
	public Res toRes() {
		Res res = Res.ofRes(this.getCode(), this.getMsg());
		if (Objects.nonNull(this.getData())) {
			Object data = this.getData();
			if (data instanceof Page) {
				res.putAll(((Page<?>) data).toRes());
			} else if (data instanceof Pager) {
				res.putAll(((Pager<?>) data).toObject());
			} else {
				res.put("data", data);
			}
		}
		if (Objects.nonNull(this.getErr())) {
			res.put("err", this.getErr());
		}
		return res;
	}

	/**
	 * 转成对象
	 *
	 * @return obj
	 */
	@Override
	public Object toObject() {
		return toRes();
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

	/**
	 * 转成Json
	 *
	 * @return Json
	 */
	@Override
	public @NotNull String toJson() {
		return JsonUtils.toJson(this);
	}

	public @NotNull Object readResolve() throws Exception {
		return this.getClass().getConstructor().newInstance();
	}

}
