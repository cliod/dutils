package com.wobangkj.exception;

import com.wobangkj.api.BaseEnum;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 认证异常处理
 *
 * @author Cliod
 * @date 2019/7/16
 * @desc exception
 */
@Getter
public class AuthorizeException extends AppException {

    private String auth;

    public AuthorizeException(@NotNull BaseEnum<? extends Enum<?>> re) {
        super(re);
    }

    public AuthorizeException(Integer code, String s) {
        super(code, s);
    }

    public AuthorizeException(@NotNull BaseEnum<? extends Enum<?>> re, @NotNull Object detail) {
        super(re);
        this.auth = detail.toString();
    }

    public AuthorizeException(Integer code, String s, @NotNull Object detail) {
        super(code, s);
        this.auth = detail.toString();
    }
}
