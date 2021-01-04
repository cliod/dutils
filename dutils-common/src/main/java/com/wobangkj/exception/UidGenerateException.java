package com.wobangkj.exception;

import com.wobangkj.api.EnumTextMsg;
import com.wobangkj.api.Response;

/**
 * id生成器异常
 *
 * @author cliod
 * @since 7/8/20 3:37 PM
 */
public class UidGenerateException extends AppException {

	/**
	 * Default constructor
	 */
	public UidGenerateException() {
		super((EnumTextMsg) () -> "uid生成异常");
	}

	public UidGenerateException(String message, Throwable cause) {
		super((EnumTextMsg) () -> message, cause);
	}

	public UidGenerateException(String message) {
		super((EnumTextMsg) () -> message);
	}

	public UidGenerateException(String msgFormat, Object... args) {
		super((EnumTextMsg) () -> String.format(msgFormat, args));
	}

	public UidGenerateException(Throwable cause) {
		super(Response.SHOW_CODE, cause);
	}

}
