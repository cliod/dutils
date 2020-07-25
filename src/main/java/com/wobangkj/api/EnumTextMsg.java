package com.wobangkj.api;

/**
 * 枚举类型
 *
 * @author cliod
 * @since 2019/12/27
 * package : com.wobangkj.api
 */
public interface EnumTextMsg extends EnumMsg {

    /**
     * 获取code
     *
     * @return code
     */
    default Integer getCode() {
        return 271;
    }

    default String getMsg(String msg, Object... args) {
        return getMsg() + " " + String.format(msg, args);
    }

    default String getMsg(Object... args) {
        return String.format(getMsg(), args);
    }
}
