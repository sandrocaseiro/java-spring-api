package dev.sandrocaseiro.template.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DateUtilTest {
    @Test
    void testStringToDateShouldReturnNullIfNotInformed() {
        assertThat(DateUtil.toDate(null)).isNull();
        assertThat(DateUtil.toDate("")).isNull();
    }

    @Test
    void testStringToDateShouldThrowIfInvalidFormat() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("202-02-27"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-2-27"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-2"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020/02/27"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("27/02/2020"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("27-02-2020"));
    }

    @Test
    void testStringToDateShouldThrowIfInvalidDate() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-31"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-13-27"));
    }

    @Test
    void testStringToDateShouldReturnDate() {
        LocalDate date = DateUtil.toDate("2020-02-27");

        assertThat(date.getYear()).isEqualTo(2020);
        assertThat(date.getMonthValue()).isEqualTo(2);
        assertThat(date.getDayOfMonth()).isEqualTo(27);
    }

    @Test
    void testStringToDatePatternShouldReturnNullIfNotInformed() {
        String pattern = "uuuu-MM-dd";
        assertThat(DateUtil.toDate(null, pattern)).isNull();
        assertThat(DateUtil.toDate("", pattern)).isNull();
    }

    @Test
    void testStringToDatePatternShouldThrowIfInvalidFormat() {
        String pattern = "uuuu-MM-dd";
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("202-02-27", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-2-27", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-2", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020/02/27", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("27/02/2020", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("27-02-2020", pattern));
    }

    @Test
    void testStringToDatePatternShouldThrowIfInvalidDate() {
        String pattern = "uuuu-MM-dd";
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-31", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-13-27", pattern));
    }

    @Test
    void testStringToDatePatternShouldThrowIdInvalidPattern() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-27", "yyyy-MM-dd"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-27", "dd-MM-uuuu"));
    }

    @Test
    void testStringToDatePatternShouldReturnDate() {
        LocalDate date = DateUtil.toDate("2020-02-27", "uuuu-MM-dd");

        assertThat(date.getYear()).isEqualTo(2020);
        assertThat(date.getMonthValue()).isEqualTo(2);
        assertThat(date.getDayOfMonth()).isEqualTo(27);
    }

    @Test
    void testStringToDateTimeShouldReturnNullIfNotInformed() {
        assertThat(DateUtil.toDateTime(null)).isNull();
        assertThat(DateUtil.toDateTime("")).isNull();
    }

    @Test
    void testStringToDateTimeShouldThrowIfInvalidFormat() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020/02/27T10:43:0Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("202-02-27 10:43:00"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-27T1:43:00Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-2T10:4:00Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-27T10:43:0Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("27-02-2020T10:43:00Z"));
    }

    @Test
    void testStringToDateTimeShouldThrowIfInvalidDateTime() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-31T10:43:00Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-13-27T10:43:00Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-13-27T24:43:00Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-13-27T10:60:00Z"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("27-02-2020T10:43:60Z"));
    }

    @Test
    void testStringToDateTimeShouldReturnDate() {
        LocalDateTime dateTime = DateUtil.toDateTime("2020-02-27T10:43:30Z");

        assertThat(dateTime.getYear()).isEqualTo(2020);
        assertThat(dateTime.getMonthValue()).isEqualTo(2);
        assertThat(dateTime.getDayOfMonth()).isEqualTo(27);
        assertThat(dateTime.getHour()).isEqualTo(10);
        assertThat(dateTime.getMinute()).isEqualTo(43);
        assertThat(dateTime.getSecond()).isEqualTo(30);
    }

    @Test
    void testStringToDateTimePatternShouldReturnNullIfNotInformed() {
        String pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'";
        assertThat(DateUtil.toDateTime(null, pattern)).isNull();
        assertThat(DateUtil.toDateTime("", pattern)).isNull();
    }

    @Test
    void testStringToDateTimePatternShouldThrowIfInvalidFormat() {
        String pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'";
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020/02/27T10:43:0Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("202-02-27 10:43:00", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-27T1:43:00Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-2T10:4:00Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-27T10:43:0Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("27-02-2020T10:43:00Z", pattern));
    }

    @Test
    void testStringToDateTimePatternShouldThrowIfInvalidDateTime() {
        String pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'";
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-02-31T10:43:00Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-13-27T10:43:00Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-13-27T24:43:00Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("2020-13-27T10:60:00Z", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDateTime("27-02-2020T10:43:60Z", pattern));
    }

    @Test
    void testStringToDateTimePatternShouldThrowIdInvalidPattern() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-31T10:43:00Z", "yyyy-MM-dd'T'HH:mm:ss'Z'"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toDate("2020-02-31T10:43:00Z", "uuuu-MM-dd HH:mm:ss"));
    }

    @Test
    void testStringToDateTimePatternShouldReturnDate() {
        LocalDateTime dateTime = DateUtil.toDateTime("2020-02-27T10:43:30Z", "uuuu-MM-dd'T'HH:mm:ss'Z'");

        assertThat(dateTime.getYear()).isEqualTo(2020);
        assertThat(dateTime.getMonthValue()).isEqualTo(2);
        assertThat(dateTime.getDayOfMonth()).isEqualTo(27);
        assertThat(dateTime.getHour()).isEqualTo(10);
        assertThat(dateTime.getMinute()).isEqualTo(43);
        assertThat(dateTime.getSecond()).isEqualTo(30);
    }

    @Test
    void testStringToTimeShouldReturnNullIfNotInformed() {
        assertThat(DateUtil.toTime(null)).isNull();
        assertThat(DateUtil.toTime("")).isNull();
    }

    @Test
    void testStringToTimeShouldThrowIfInvalidFormat() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("1043"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10 43"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10-43"));
    }

    @Test
    void testStringToTimeShouldThrowIfInvalidTime() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("24:43"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10:60"));
    }

    @Test
    void testStringToTimeShouldReturnTime() {
        LocalTime time = DateUtil.toTime("10:43");

        assertThat(time.getHour()).isEqualTo(10);
        assertThat(time.getMinute()).isEqualTo(43);
    }

    @Test
    void testStringToTimePatternShouldReturnNullIfNotInformed() {
        String pattern = "HH:mm";
        assertThat(DateUtil.toTime(null, pattern)).isNull();
        assertThat(DateUtil.toTime("", pattern)).isNull();
    }

    @Test
    void testStringToTimePatternShouldThrowIfInvalidFormat() {
        String pattern = "HH:mm";
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("1043", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10 43", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10-43", pattern));
    }

    @Test
    void testStringToTimePatternShouldThrowIfInvalidTime() {
        String pattern = "HH:mm";
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("24:43", pattern));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10:60", pattern));
    }

    @Test
    void testStringToTimePatternShouldThrowIdInvalidPattern() {
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10:43", "HH mm"));
        assertThatExceptionOfType(DateTimeParseException.class).isThrownBy(() -> DateUtil.toTime("10:43", "hh:MM"));
    }

    @Test
    void testStringToTimePatternShouldReturnTime() {
        LocalTime time = DateUtil.toTime("10:43", "HH:mm");

        assertThat(time.getHour()).isEqualTo(10);
        assertThat(time.getMinute()).isEqualTo(43);
    }

    @Test
    void testDateToStringShouldReturnNullIfNotInformed() {
        assertThat(DateUtil.toString((LocalDate)null)).isNull();
    }

    @Test
    void testDateToStringShouldReturnFormattedDate() {
        assertThat(LocalDate.of(2020, 2, 27)).isNotNull().isEqualTo("2020-02-27");
    }

    @Test
    void testDateToStringPatternShouldReturnNullIfNotInformed() {
        String pattern = "uuuu-MM-dd";
        assertThat(DateUtil.toString((LocalDate)null, pattern)).isNull();
    }

    @Test
    void testDateToStringPatternShouldThrowIdInvalidPattern() {
        LocalDate date = LocalDate.of(2020, 2, 27);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> DateUtil.toString(date, "uuuu-mm-aa"));
    }

    @Test
    void testDateToStringPatternShouldReturnFormattedDate() {
        LocalDate date = LocalDate.of(2020, 2, 27);
        String pattern = "uuuu-MM-dd";
        assertThat(DateUtil.toString(date, pattern)).isNotNull().isEqualTo("2020-02-27");
    }

    @Test
    void testDateTimeToStringShouldReturnNullIfNotInformed() {
        assertThat(DateUtil.toString((LocalDateTime)null)).isNull();
    }

    @Test
    void testDateTimeToStringShouldReturnFormattedDate() {
        assertThat(DateUtil.toString(LocalDateTime.of(2020, 2, 27, 10, 43, 30))).isNotNull().isEqualTo("2020-02-27T10:43:30Z");
    }

    @Test
    void testDateTimeToStringPatternShouldReturnNullIfNotInformed() {
        String pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'";
        assertThat(DateUtil.toString((LocalDateTime)null, pattern)).isNull();
    }

    @Test
    void testDateTimeToStringPatternShouldThrowIdInvalidPattern() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 2, 27, 10, 43, 30);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> DateUtil.toString(dateTime, "uuuu-mm-aaTHH:mm:ss'Z'"));
    }

    @Test
    void testDateTimeToStringPatternShouldReturnFormattedDate() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 2, 27, 10, 43, 30);
        String pattern = "uuuu-MM-dd'T'HH:mm:ss'Z'";
        assertThat(DateUtil.toString(dateTime, pattern)).isNotNull().isEqualTo("2020-02-27T10:43:30Z");
    }

    @Test
    void testTimeToStringShouldReturnNullIfNotInformed() {
        assertThat(DateUtil.toString((LocalTime) null)).isNull();
    }

    @Test
    void testTimeToStringShouldReturnFormattedDate() {
        assertThat(DateUtil.toString(LocalTime.of(10, 43))).isNotNull().isEqualTo("10:43");
    }

    @Test
    void testTimeToStringPatternShouldReturnNullIfNotInformed() {
        String pattern = "HH:mm";
        assertThat(DateUtil.toString((LocalTime)null, pattern)).isNull();
    }

    @Test
    void testTimeToStringPatternShouldThrowIdInvalidPattern() {
        LocalTime time = LocalTime.of(10, 43);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> DateUtil.toString(time, "aa:mm"));
    }

    @Test
    void testTimeToStringPatternShouldReturnFormattedDate() {
        LocalTime time = LocalTime.of(10, 43);
        String pattern = "HH:mm";
        assertThat(DateUtil.toString(time, pattern)).isNotNull().isEqualTo("10:43");
    }
}
