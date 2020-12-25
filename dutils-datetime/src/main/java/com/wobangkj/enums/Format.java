package com.wobangkj.enums;

import com.wobangkj.api.DateFormat;
import com.wobangkj.api.EnumValue;
import lombok.Getter;

/**
 * 时间工具
 *
 * @author cliod
 * @since 2019/11/15
 */
public enum Format implements EnumValue<String>, DateFormat {

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
	FORMAT_DOT_DATE("yyyy.MM.dd"),
	FORMAT_DOT_DATETIME("yyyy.MM.dd HH:mm:ss"),

	DATE("yyyyMMdd"),
	TIME("HHmmss"),
	DATETIME("yyyyMMddHHmmss"),
	DATE_DEFAULT("yyyy-MM-dd"),
	TIME_DEFAULT("HH:mm:ss"),
	DATETIME_DEFAULT("yyyy-MM-dd HH:mm:ss"),
	SLASH_DATE("yyyy/MM/dd"),
	SLASH_DATETIME("yyyy/MM/dd HH:mm:ss"),
	DOT_DATE("yyyy.MM.dd"),
	DOT_DATETIME("yyyy.MM.dd HH:mm:ss"),
	;

	@Getter
	private final String pattern;

	Format(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String value() {
		return pattern;
	}
}