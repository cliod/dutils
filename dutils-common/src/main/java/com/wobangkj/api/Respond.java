package com.wobangkj.api;

import com.wobangkj.bean.Page;
import com.wobangkj.bean.Res;
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
 * 旧版结果API
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
	public static @NotNull <T> Result<T> ok() {
		return ok((T) null);
	}

	/**
	 * 对象返回
	 */
	public static @NotNull <T> Result<T> ok(T o) {
		return ok(200, "成功", o);
	}

	/**
	 * 分页返回
	 */
	@Deprecated
	public static @NotNull <T> Result<Page<T>> ok(long length, List<T> list) {
		return of(200, "成功", Page.of(length, list));
	}

	public static @NotNull <T> Result<Page<T>> ok(Page<T> page) {
		return of(200, "成功", page);
	}

	/**
	 * 自定义字段, value 返回
	 */
	public static @NotNull <V> Result<Map<String, Object>> ok(String valueName, V value) {
		return ok(Res.of(valueName, value));
	}

	/**
	 * 多自定义字段, value 返回
	 */
	@SafeVarargs
	@Deprecated
	public static @NotNull <V> Result<Map<String, Object>> ok(@NotNull String[] valueNames, @NotNull V... values) {
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
	public static @NotNull <V> Result<Map<String, Object>> ok(@NotNull String titles, V... values) {
		return ok(titles.split(","), values);
	}

	/**
	 * 非默认返回信息返回
	 */
	public static @NotNull <T> Result<T> ok(@NotNull EnumMsg re, T o) {
		return ok(re.getCode(), re.getMsg(), o);
	}

	/**
	 * 其他信息返回
	 */
	public static @NotNull <T> Result<T> ok(int code, String msg, T o) {
		return of(code, msg, o);
	}

	/**
	 * 已处理失败返回
	 */

	public static @NotNull Result<Object> fail(String msg) {
		return of(271, msg);
	}

	/**
	 * 已处理失败返回
	 */

	public static @NotNull Result<Object> fail(Integer code, String msg) {
		return of(code, msg);
	}

	/**
	 * 失败返回,携带系统错误信息
	 */
	public static @NotNull Result<Object> fail(@NotNull EnumMsg re, Throwable err) {
		return of(re.getCode(), re.getMsg(), err.getMessage(), null);
	}

	/**
	 * 失败返回
	 */
	public static @NotNull Result<Object> fail(@NotNull EnumMsg re) {
		return of(re.getCode(), re.getMsg());
	}

	/**
	 * 已处理失败返回
	 */
	@Deprecated
	public static @NotNull Result<Object> fail(@NotNull EnumMsg re, @NotNull EnumMsg err) {
		return of(re.getCode(), re.getMsg(), err.toObject(), null);
	}

	/**
	 * 已处理失败返回
	 */
	public static @NotNull Result<Object> fail(@NotNull ResultEnum re, int code, String msg) {
		return of(re.getCode(), re.getMsg(), new HashMap<String, Object>(16) {{
			put("code", code);
			put("msg", msg);
		}}, null);
	}

	/**
	 * 未知异常
	 */
	public static @NotNull Result<Object> error() {
		return of(500, "系统错误");
	}

	/**
	 * 位置异常,携带信息
	 */
	public static @NotNull Result<Object> error(@NotNull EnumMsg err) {
		return of(500, "未知错误", err.toObject(), null);
	}

	/**
	 * 未知异常,携带信息
	 */
	public static @NotNull Result<Object> error(Throwable msg) {
		return of(500, "未知错误", msg, null);
	}

	@Deprecated
	public static @NotNull Builder build(String title, Object data) {
		return new Builder() {{
			setData(new HashMap<String, Object>(16) {{
				put(title, data);
			}});
		}};
	}

	@Deprecated
	public static @NotNull Builder build() {
		return new Builder() {{
			setData(new HashMap<>(16));
		}};
	}

	public static @NotNull Builder builder(String title, Object data) {
		return new Builder() {{
			setData(new HashMap<String, Object>(16) {{
				put(title, data);
			}});
		}};
	}

	public static @NotNull Builder builder() {
		return new Builder() {{
			setData(new HashMap<>(16));
		}};
	}

	@Data
	public static class Builder {
		private Map<String, Object> data;

		public @NotNull Builder put(String title, Object data) {
			this.data.put(title, data);
			return this;
		}

		@Deprecated
		public @NotNull Builder put(String title, Date data) {
			return this.put(title, data, "yyyy-MM-dd HH:mm:ss");
		}

		@Deprecated
		public @NotNull Builder put(String title, @NotNull Date data, String pattern) {
			this.data.put(title, DateTimeFormatter.ofPattern(pattern)
					.format(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
			return this;
		}

		@Deprecated
		public @NotNull Result<?> ok() {
			return Respond.ok(this.data);
		}

		public @NotNull Result<?> build() {
			return Respond.ok(this.data);
		}

		void setData(Map<String, Object> data) {
			this.data = data;
		}
	}

}
