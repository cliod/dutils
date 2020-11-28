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
@Deprecated
public class AuthorizeException extends AppException {

    @Deprecated
    public String getAuth() {
        return null;
    }

    public AuthorizeException(@NotNull EnumMsg re) {
        super(re);
    }

    public AuthorizeException(Integer code, String s) {
        super(code, s);
    }

    public AuthorizeException(Integer code, Throwable cause) {
        super(code, cause);
    }

    public AuthorizeException(@NotNull EnumMsg re, Throwable cause) {
        super(re, cause);
    }
}
