package dev.sandrocaseiro.template.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public final class DateUtil {
    private static final String DATE_PATTERN = "uuuu-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = "uuuu-MM-dd'T'HH:mm:ss'Z'";

    private DateUtil() { }

    public static LocalDate toDate(String dateString) {
        return toDate(dateString, DATE_PATTERN);
    }

    public static LocalDate toDate(String dateString, String pattern) {
        if (StringUtils.isEmpty(dateString))
            return null;
        DateTimeFormatter dtf = getFormatter(pattern);

        return LocalDate.parse(dateString, dtf);
    }

    public static LocalDateTime toDateTime(String dateTimeString) {
        return toDateTime(dateTimeString, DATETIME_PATTERN);
    }

    public static LocalDateTime toDateTime(String dateTimeString, String pattern) {
        if (StringUtils.isEmpty(dateTimeString))
            return null;

        DateTimeFormatter dtf = getFormatter(pattern);

        return LocalDateTime.parse(dateTimeString, dtf);
    }

    public static LocalTime toTime(String timeString) {
        return toTime(timeString, TIME_PATTERN);
    }

    public static LocalTime toTime(String timeString, String pattern) {
        if (StringUtils.isEmpty(timeString))
            return null;
        DateTimeFormatter dtf = getFormatter(pattern);

        return LocalTime.parse(timeString, dtf);
    }

    public static String toString(LocalDate date) {
        return toString(date, DATE_PATTERN);
    }

    public static String toString(LocalDate date, String pattern) {
        if (date == null)
            return null;

        DateTimeFormatter dtf = getFormatter(pattern);
        return date.format(dtf);
    }

    public static String toString(LocalDateTime dateTime) {
        return toString(dateTime, DATETIME_PATTERN);
    }

    public static String toString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null)
            return null;

        DateTimeFormatter dtf = getFormatter(pattern);
        return dateTime.format(dtf);
    }

    public static String toString(LocalTime time) {
        return toString(time, TIME_PATTERN);
    }

    public static String toString(LocalTime time, String pattern) {
        if (time == null)
            return null;

        DateTimeFormatter dtf = getFormatter(pattern);
        return time.format(dtf);
    }

    private static DateTimeFormatter getFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
    }
}
