package com.wobangkj.exception;

import com.wobangkj.api.BaseEnum;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 空异常处理
 *
 * @author cliod
 * @date 19-7-18
 * @desc exception
 */
@Getter
public class NullObjectException extends AppException {

    private Object detail;

    public NullObjectException(@NotNull BaseEnum<? extends Enum<?>> re) {
        super(re);
    }

    public NullObjectException(Integer code, String s) {
        super(code, s);
    }

    public NullObjectException(@NotNull BaseEnum<? extends Enum<?>> re, Object detail) {
        super(re);
        this.detail = detail;
    }

    public NullObjectException(Integer code, String s, Object detail) {
        super(code, s);
        this.detail = detail;
    }

    public NullObjectException(Throwable cause, Integer code, Object detail) {
        super(cause, code);
        this.detail = detail;
    }
}
