package com.wobangkj.exception;

import com.wobangkj.api.BaseEnum;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * 统一异常类
 *
 * @author cliod
 * @date 19-7-16
 * @desc exception
 */
@Getter
public class AppException extends RuntimeException {
    /**
     * 异常里面的详情
     */
    private Integer code;

    public AppException(@NotNull BaseEnum<? extends Enum<?>> re) {
        super(re.getMsg());
        this.code = re.getCode();
    }

    public AppException(Integer code, String s) {
        super(s);
        this.code = code;
    }

    public AppException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }
}
