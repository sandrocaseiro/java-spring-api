package com.sandrocaseiro.apitemplate.validations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CpfValidatorTest {
    CpfValidator validator = new CpfValidator();

    @Test
    void testNullValueIsNotValid() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    void testInvalidCpfShouldNotValidate() {
        assertThat(validator.isValid("", null)).isFalse();
        assertThat(validator.isValid("12345678912", null)).isFalse();
        assertThat(validator.isValid("1234", null)).isFalse();
        assertThat(validator.isValid("16352660097", null)).isFalse();
        assertThat(validator.isValid("999.233.790-78", null)).isFalse();
    }

    @Test
    void testShouldValidateCorrectCpf() {
        assertThat(validator.isValid("01846941083", null)).isTrue();
        assertThat(validator.isValid("48231831002", null)).isTrue();
        assertThat(validator.isValid("44045178074", null)).isTrue();
    }
}
