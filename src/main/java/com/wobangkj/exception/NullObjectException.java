package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 空异常处理
 *
 * @author cliod
 * @desc exception
 * @since 19-7-18
 */
@Getter
public class NullObjectException extends AccessException {

    public NullObjectException(@NotNull EnumMsg re) {
        super(re);
    }

    public NullObjectException(Integer code, String s) {
        super(code, s);
    }

    public NullObjectException(Integer code, Throwable s) {
        super(code, s);
    }

    public NullObjectException(@NotNull EnumMsg re, Object detail) {
        super(re, detail);
    }

    public NullObjectException(Integer code, String s, Object detail) {
        super(code, s, detail);
    }

    public NullObjectException(Integer code, Throwable cause, Object detail) {
        super(code, cause, detail);
    }
}
