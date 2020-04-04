package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 访问异常
 *
 * @author cliod
 * @date 19-7-18
 * @desc exception
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

    public AccessException(@NotNull EnumMsg re, Object access) {
        super(re);
        this.access = access;
    }

    public AccessException(Integer code, String s, Object access) {
        super(code, s);
        this.access = access;
    }

    public AccessException(Throwable cause, Integer code, Object access) {
        super(cause, code);
        this.access = access;
    }
}
