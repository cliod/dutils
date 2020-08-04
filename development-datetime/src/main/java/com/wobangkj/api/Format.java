package com.wobangkj.api;

import lombok.Getter;

/**
 * 时间工具
 *
 * @author cliod
 * @date 2019/11/15
 * package : com.wobangkj.api
 */
@Getter
public enum Format {

    /**
     * 时间格式
     */
    FORMAT_DATE("yyyyMMdd"),
    FORMAT_TIME("HHmmss"),
    FORMAT_DATETIME("yyyyMMddHHmmss"),
    FORMAT_DATE_DEFAULT("yyyy-MM-dd"),
    FORMAT_TIME_DEFAULT("HH:mm:ss"),
    FORMAT_DATETIME_DEFAULT("yyyy-MM-dd HH:mm:ss"),
    FORMAT_SLASH_DATE("yyyy/MM/dd"),
    FORMAT_SLASH_DATETIME("yyyy/MM/dd HH:mm:ss"),
    ;

    private final String patten;

    Format(String patten) {
        this.patten = patten;
    }
}