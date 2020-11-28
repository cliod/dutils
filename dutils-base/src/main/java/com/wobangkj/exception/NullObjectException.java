package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 空异常处理
 *
 * @author cliod
 * @since 19-7-18
 */
@Deprecated
public class NullObjectException extends AppException {

	@Deprecated
	public Object getDetail() {
		return null;
	}

	public NullObjectException(@NotNull EnumMsg re) {
		super(re);
	}

	public NullObjectException(Integer code, String s) {
		super(code, s);
	}

	public NullObjectException(Integer code, Throwable cause) {
		super(code, cause);
	}

	public NullObjectException(@NotNull EnumMsg re, Throwable cause) {
		super(re, cause);
	}
}
