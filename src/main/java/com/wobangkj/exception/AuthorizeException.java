package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 认证异常处理
 *
 * @author Cliod
 * @see AppException
 * @since 2019/7/16
 */
@Getter
public class AuthorizeException extends AppException {

	private static final long serialVersionUID = -6521618167201387588L;
	private String auth;

	public AuthorizeException(@NotNull EnumMsg re) {
		super(re);
	}

	public AuthorizeException(Integer code, String s) {
		super(code, s);
	}

	public AuthorizeException(@NotNull EnumMsg re, @NotNull Object detail) {
		super(re);
		this.auth = detail.toString();
	}

	public AuthorizeException(Integer code, String s, @NotNull Object detail) {
		super(code, s);
		this.auth = detail.toString();
	}

	public AuthorizeException(@NotNull EnumMsg re, Throwable cause, String auth) {
		super(re, cause);
		this.auth = auth;
	}
}
