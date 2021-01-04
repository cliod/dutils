package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import org.jetbrains.annotations.NotNull;

/**
 * 通用参数异常
 *
 * @author cliod
 * @see IllegalArgumentException
 * @since 11/28/20 10:10 AM
 */
@Deprecated
public class ParameterException extends AppException {

	public ParameterException(@NotNull EnumMsg re) {
		super(re);
	}

	public ParameterException(Integer code, String s) {
		super(code, s);
	}

	public ParameterException(Integer code, Throwable cause) {
		super(code, cause);
	}

	public ParameterException(@NotNull EnumMsg re, Throwable cause) {
		super(re, cause);
	}
}
