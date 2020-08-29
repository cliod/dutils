package com.wobangkj.exception;

import com.wobangkj.api.EnumMsg;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 资源丢失
 *
 * @author cliod
 * @since 19-7-16
 */
@Getter
public class NotFoundException extends AccessException {

    private Object resources;

    public NotFoundException(@NotNull EnumMsg re) {
        super(re);
    }

    public NotFoundException(Integer code, String s) {
        super(code, s);
    }

    public NotFoundException(@NotNull EnumMsg re, Object resources) {
        super(re);
        this.resources = resources;
    }

    public NotFoundException(Integer code, String s, Object resources) {
        super(code, s);
        this.resources = resources;
    }

    public NotFoundException(@NotNull EnumMsg re, Object access, Object resources) {
        super(re, access);
        this.resources = resources;
    }

    public NotFoundException(Integer code, String s, Object access, Object resources) {
        super(code, s, access);
        this.resources = resources;
    }

    public NotFoundException(Throwable cause, Integer code, Object access, Object resources) {
        super(cause, code, access);
        this.resources = resources;
    }
}
