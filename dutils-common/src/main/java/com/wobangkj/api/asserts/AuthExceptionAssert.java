package com.wobangkj.api.asserts;

import com.wobangkj.api.EnumTextMsg;
import com.wobangkj.exception.AppException;

import java.text.MessageFormat;

/**
 * 授权异常处理接口
 *
 * @author @cliod
 * @since 4/28/20 11:57 AM
 */
@Deprecated
public interface AuthExceptionAssert extends EnumTextMsg, ExceptionAssert {
	/**
	 * 新建异常
	 *
	 * @param args 参数
	 * @return 异常
	 */
	@Override
	default AppException newException(Object... args) {
		String msg = MessageFormat.format(this.getMsg(), args);
		return new AppException(getCode(), msg);
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
		return new AppException((EnumTextMsg) () -> msg, t);
	}

	/**
	 * 获取code
	 *
	 * @return code
	 */
	@Override
	default int getStatus() {
		return this.getCode();
	}
}
