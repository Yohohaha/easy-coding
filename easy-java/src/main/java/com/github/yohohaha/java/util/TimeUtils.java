package com.github.yohohaha.java.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created at 2020/02/05 11:57:12
 *
 * @author Yohohaha
 */
public class TimeUtils {

    private static final ConcurrentHashMap<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>();



    /* source: long */

    /**
     * get formatted string from timestamp
     *
     * @param ts     timestamp
     * @param format string format
     *
     * @return formatted string
     */
    public static String getStringOfTimestamp(final long ts, String format) {
        DateTimeFormatter dateFormatter = getDateTimeFormatter(format);
        return dateFormatter.format(getDateTimeOfTimestamp(ts));
    }

    /**
     * get datetime from timestamp
     *
     * @param timestamp timestamp
     *
     * @return datetime
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }




    /* source: LocalDateTime */

    /**
     * get string from datetime
     *
     * @param localDateTime localDatetime
     * @param format        string format
     *
     * @return formatted string
     */
    public static String getStringofDateTime(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = getDateTimeFormatter(format);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * parse string to timestamp
     *
     * @param time   time string
     * @param format string format
     *
     * @return timestamp
     */
    public static long parseStringToTimestamp(String time, String format) {
        return getTimestampOfDateTime(parseStringToDateTime(time, format));
    }



    /* source: String */

    private static DateTimeFormatter getDateTimeFormatter(final String format) {
        DateTimeFormatter dateTimeFormatter = FORMATTER_MAP.get(format);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            FORMATTER_MAP.put(format, dateTimeFormatter);
        }
        return dateTimeFormatter;
    }

    /**
     * get timestamp from datetime
     *
     * @param localDateTime localDatetime
     *
     * @return timestamp
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * parse string to datetime
     *
     * @param time   time string
     * @param format string format
     *
     * @return datetime
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = getDateTimeFormatter(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * get the timestamp at 00:00 today
     *
     * @return the timestamp at 00:00 today
     */
    public static long getCurrentDayZeroPointTimestamp() {
        return getCurrentDayZeroPointCalendar().getTimeInMillis();
    }

    /**
     * get the <code>Calendar</code> obj at 00:00 today
     *
     * @return the <code>Calendar</code> obj at 00:00 today
     */
    public static Calendar getCurrentDayZeroPointCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * get the timestamp at 00:00 today
     *
     * @return the timestamp at 00:00 today
     */
    public static long getPastDayZeroPointTimestamp(int minus) {
        Calendar calendar = getCurrentDayZeroPointCalendar();
        calendar.add(Calendar.DATE, -minus);
        return calendar.getTimeInMillis();
    }

}
