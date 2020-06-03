package com.github.yohohaha.java.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * created by Yohohaha at 2020/02/28 18:12:33
 */
public class TimeUtilsTest {

    @Test
    public void getCurrentDayZeroPointTimestamp() {
        System.out.println(TimeUtils.getCurrentDayZeroPointTimestamp());
    }

    @Test
    public void getPastDayZeroPointTimestamp() {
        System.out.println(TimeUtils.getPastDayZeroPointTimestamp(1 + 29 + 31));
    }

    @Test
    public void speedTest() {
        int count = 1;
        long s1 = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.getTimeInMillis();
        }
        long e1 = System.nanoTime();
        long s2 = System.nanoTime();
        for (int i = 0; i < count; i++) {
            LocalDateTime now = LocalDateTime.now();
            now.minus(now.getHour(), ChronoUnit.HOURS)
                .minus(now.getMinute(), ChronoUnit.MINUTES)
                .minus(now.getSecond(), ChronoUnit.SECONDS)
                .minus(now.getNano(), ChronoUnit.NANOS).toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
        }
        long e2 = System.nanoTime();
        System.out.println((e1 - s1) + "\n" + (e2 - s2));
    }
}