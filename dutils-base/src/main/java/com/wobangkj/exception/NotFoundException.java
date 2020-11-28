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
@Deprecated
public class NotFoundException extends AccessException {

	@Deprecated
	public Object getResources() {
		return null;
	}

    public NotFoundException(@NotNull EnumMsg re) {
        super(re);
    }

    public NotFoundException(Integer code, String s) {
        super(code, s);
    }

	public NotFoundException(Integer code, Throwable cause) {
		super(code, cause);
	}
}
