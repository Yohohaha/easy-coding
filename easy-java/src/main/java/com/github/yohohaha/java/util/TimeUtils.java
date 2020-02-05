package com.github.yohohaha.java.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created at 2020/02/05 11:57:12
 *
 * @author Yohohaha
 */
public class TimeUtils {

    /* source: long */

    private static final ConcurrentHashMap<String, DateTimeFormatter> FORMATTER_MAP = new ConcurrentHashMap<>();

    /**
     * 将时间戳转为指定格式的字符串
     *
     * @param ts
     * @param format
     *
     * @return
     */
    public static String getStringOfTimestamp(final long ts, String format) {
        DateTimeFormatter dateFormatter = getDateTimeFormatter(format);
        return dateFormatter.format(getDateTimeOfTimestamp(ts));
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
     * 将long类型的timestamp转为LocalDateTime
     *
     * @param timestamp
     *
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }




    /* source: LocalDateTime */

    public static long parseStringToTimeStamp(String time, String format) {
        return getTimestampOfDateTime(parseStringToDateTime(time, format));
    }

    /**
     * 将LocalDateTime转为long类型的timestamp
     *
     * @param localDateTime
     *
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 将某时间字符串转为自定义时间格式的LocalDateTime
     *
     * @param time
     * @param format
     *
     * @return
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = getDateTimeFormatter(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * 给定format，获取当前时间的格式化字符串
     *
     * @param format
     *
     * @return
     */
    public static String getStringofDateTime(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = getDateTimeFormatter(format);
        return LocalDateTime.now().format(formatter);
    }

}
