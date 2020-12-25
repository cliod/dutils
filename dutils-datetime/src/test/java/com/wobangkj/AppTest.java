package com.wobangkj;

import com.wobangkj.enums.Format;
import com.wobangkj.utils.DateUtils;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);

        LocalDate date = LocalDate.ofEpochDay(Instant.now().toEpochMilli() / 1000 / 3600 / 24);
        System.out.println(date.toString());
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        System.out.println(DateUtils.format(dateTime, Format.DOT_DATETIME));

//        LocalDate date1 = LocalDate.from(YearMonth.now());
//        System.out.println(date1.toString());
        System.out.println("Hello World!");
    }
}
