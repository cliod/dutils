package com.wobangkj.api.asserts;

import com.wobangkj.api.EnumMsg;
import com.wobangkj.exception.AppException;
import com.wobangkj.exception.AuthorizeException;

import java.text.MessageFormat;

/**
 * 授权异常处理接口
 *
 * @author @cliod
 * @since 4/28/20 11:57 AM
 * package: com.wobangkj.api
 */
public interface AuthExceptionAssert extends EnumMsg, ExceptionAssert {
	/**
	 * 新建异常
	 *
	 * @param args 参数
	 * @return 异常
	 */
	@Override
	default AppException newException(Object... args) {
		String msg = MessageFormat.format(this.getMsg(), args);
		return new AuthorizeException(this, msg);
	}

	/**
	 * 新建异常
	 *
	 * @param t    接收的异常
	 * @param args 参数
	 * @return 异常
	 */
	@Override
	default AppException newException(Throwable t, Object... args) {
		String msg = MessageFormat.format(this.getMsg(), args);
		return new AuthorizeException(this, t, msg);
	}

	/**
	 * 获取code
	 *
	 * @return code
	 */
	@Override
	default Integer getCode() {
		return 271;
	}
}
