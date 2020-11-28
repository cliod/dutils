package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 访问异常
 *
 * @author cliod
 * @since 19-7-18
 */
@Deprecated
public class AccessException extends AppException {

	public AccessException(@NotNull EnumMsg re) {
		super(re);
	}

	public AccessException(Integer code, String s) {
		super(code, s);
	}

	public AccessException(Integer code, Throwable cause) {
		super(code, cause);
	}

	@Deprecated
	public Object getAccess() {
		return null;
	}
}
