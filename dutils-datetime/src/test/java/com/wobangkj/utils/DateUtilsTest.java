package com.wobangkj.utils;

import com.wobangkj.enums.Format;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

/**
 * @author cliod
 * @since 12/29/20 11:09 AM
 */
public class DateUtilsTest {

	@Test
	public void test() {
		LocalDate date = LocalDate.ofEpochDay(Instant.now().toEpochMilli() / 1000 / 3600 / 24);
		System.out.println(date.toString());
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		System.out.println(DateUtils.format(dateTime, Format.DOT_DATETIME));
	}
}