package com.wobangkj.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wobangkj.api.SessionSerializable;
import com.wobangkj.utils.JsonUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

/**
 * 旧版结果封装
 *
 * @author cliod
 * @since 11/28/20 3:05 PM
 */
@Data
@Deprecated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements SessionSerializable {
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
	 * @param o    T
	 * @return result
	 */
	public static @NotNull <T> Result<T> of(int code, String msg, T o) {
		return of(code, msg, null, o);
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
