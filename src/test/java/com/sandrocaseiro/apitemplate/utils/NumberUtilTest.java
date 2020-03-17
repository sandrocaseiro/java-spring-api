package com.sandrocaseiro.apitemplate.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class NumberUtilTest {
    @Test
    void testIntParseNullOrEmptyShouldReturnDefaultValue() {
        assertThat(NumberUtil.toInt(null, 0)).isEqualTo(0);
        assertThat(NumberUtil.toInt("", 1)).isEqualTo(1);
    }

    @Test
    void testIntParseInvalidShouldThrow() {
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(() -> NumberUtil.toInt("aaa", 0));
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(() -> NumberUtil.toInt("11,5", 0));
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(() -> NumberUtil.toInt("11.5", 0));
    }

    @Test
    void testIntParseShouldParseToInt() {
        assertThat(NumberUtil.toInt("10", 0)).isEqualTo(10);
        assertThat(NumberUtil.toInt("22", 0)).isEqualTo(22);
        assertThat(NumberUtil.toInt("01", 0)).isEqualTo(1);
    }
}
