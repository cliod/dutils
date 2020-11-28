package com.wobangkj.api;

import com.wobangkj.bean.Page;
import com.wobangkj.bean.Result;
import com.wobangkj.enums.ResultEnum;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wobangkj.bean.Result.of;

/**
 * 结果API
 *
 * @author cliod
 * @since 11/28/20 3:07 PM
 */
@Deprecated
public class Respond {
	private Respond() {
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
		return of(200, "成功", Page.of(length, list));
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
	@NotNull
	@SafeVarargs
	@Deprecated
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
		return of(code, msg, o);
	}

	/**
	 * 未知异常
	 */
	@NotNull
	public static Result<Object> error() {
		return of(500, "系统错误", null);
	}

	/**
	 * 位置异常,携带信息
	 */
	@NotNull
	public static Result<Object> error(@NotNull EnumMsg err) {
		return of(500, "未知错误", err.toObject(), null);
	}

	/**
	 * 未知异常,携带信息
	 */
	@NotNull
	public static Result<Object> error(Throwable msg) {
		return of(500, "未知错误", msg, null);
	}

	/**
	 * 失败返回,携带系统错误信息
	 */
	@NotNull
	public static Result<Object> fail(@NotNull ResultEnum re, Throwable err) {
		return of(re.getCode(), re.getMsg(), err, null);
	}

	/**
	 * 失败返回
	 */
	@NotNull
	public static Result<Object> fail(@NotNull ResultEnum re) {
		return of(re.getCode(), re.getMsg(), re.toObject(), null);
	}

	/**
	 * 已处理失败返回
	 */
	@NotNull
	public static Result<Object> fail(@NotNull ResultEnum re, @NotNull EnumMsg err) {
		return of(re.getCode(), re.getMsg(), err.toObject(), null);
	}

	/**
	 * 已处理失败返回
	 */
	@NotNull
	public static Result<Object> fail(@NotNull ResultEnum re, int code, String msg) {
		return of(re.getCode(), re.getMsg(), new HashMap<String, Object>(16) {{
			put("code", code);
			put("msg", msg);
		}}, null);
	}

	@NotNull
	public static Builder build(String title, Object data) {
		return new Builder() {{
			setData(new HashMap<String, Object>(16) {{
				put(title, data);
			}});
		}};
	}

	@NotNull
	public static Builder build() {
		return new Builder() {{
			setData(new HashMap<>(16));
		}};
	}

	@Data
	public static class Builder {
		private Map<String, Object> data;

		@NotNull
		public Builder put(String title, Object data) {
			this.data.put(title, data);
			return this;
		}

		@NotNull
		@Deprecated
		public Builder put(String title, Date data) {
			return this.put(title, data, "yyyy-MM-dd HH:mm:ss");
		}

		@NotNull
		@Deprecated
		public Builder put(String title, @NotNull Date data, String pattern) {
			this.data.put(title, DateTimeFormatter.ofPattern(pattern)
					.format(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
			return this;
		}

		public @NotNull Result<?> ok() {
			return Respond.ok(this.data);
		}

		void setData(Map<String, Object> data) {
			this.data = data;
		}
	}

}
