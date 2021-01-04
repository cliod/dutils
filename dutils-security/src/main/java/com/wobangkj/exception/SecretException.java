package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import org.jetbrains.annotations.NotNull;

/**
 * 秘钥获取设置异常
 *
 * @author cliod
 * @version 1.0
 * @since 2021-01-04 13:03:08
 */
public class SecretException extends AppException {

	public SecretException(@NotNull EnumMsg re) {
		super(re);
	}

	public SecretException(Integer code, String s) {
		super(code, s);
	}

	public SecretException(Integer code, Throwable cause) {
		super(code, cause);
	}

	public SecretException(@NotNull EnumMsg re, Throwable cause) {
		super(re, cause);
	}
}
