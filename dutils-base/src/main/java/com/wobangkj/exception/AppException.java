package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 统一异常类
 *
 * @author cliod
 * @see RuntimeException
 * @since 19-7-16
 */
@Getter
public class AppException extends RuntimeException {
	/**
	 * 异常里面的详情
	 */
	private final Integer code;

	public AppException(@NotNull EnumMsg re) {
		super(re.getMsg());
		this.code = re.getCode();
	}

	public AppException(Integer code, String s) {
		super(s);
		this.code = code;
	}

	public AppException(Integer code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public AppException(@NotNull EnumMsg re, Throwable cause) {
		super(re.getMsg(), cause);
		this.code = re.getCode();
	}
}
