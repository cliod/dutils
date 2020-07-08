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
@Getter
public class AccessException extends AppException {

    private Object access;

    public AccessException(@NotNull EnumMsg re) {
        super(re);
    }

    public AccessException(Integer code, String s) {
        super(code, s);
    }

    public AccessException(Integer code, Throwable s) {
        super(code, s);
    }

    public AccessException(@NotNull EnumMsg re, Object access) {
        super(re);
        this.access = access;
    }

    public AccessException(Integer code, String s, Object access) {
        super(code, s);
        this.access = access;
    }

    public AccessException(Integer code, Throwable cause, Object access) {
        super(code, cause);
        this.access = access;
    }
}
