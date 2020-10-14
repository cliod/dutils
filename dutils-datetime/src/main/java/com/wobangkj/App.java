package com.wobangkj;

import java.time.*;

/**
 * Hello world!
 *
 * @author cliod
 */
public class App {
	public static void main(String[] args) {
		LocalDate date = LocalDate.ofEpochDay(Instant.now().toEpochMilli() / 1000 / 3600 / 24);
		System.out.println(date.toString());
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		System.out.println(dateTime.toString());

		LocalDate date1 = LocalDate.from(YearMonth.now());
		System.out.println(date1.toString());
		System.out.println("Hello World!");
	}
}
