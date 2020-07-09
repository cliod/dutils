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

    private static final long serialVersionUID = 5648923359297934582L;
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

    public NotFoundException(Integer code, Throwable cause, Object access, Object resources) {
        super(code, cause, access);
        this.resources = resources;
    }
}
