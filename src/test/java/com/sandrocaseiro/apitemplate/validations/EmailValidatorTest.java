package com.sandrocaseiro.apitemplate.validations;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailValidatorTest {
    EmailValidator validator = new EmailValidator();

    @Test
    void testNullValueIsNotValid() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    void testInvalidEmailShouldNotValidate() {
        assertThat(validator.isValid("", null)).isFalse();
        assertThat(validator.isValid("testmail.com", null)).isFalse();
        assertThat(validator.isValid("@mail.com", null)).isFalse();
        assertThat(validator.isValid(".test@mail.com", null)).isFalse();
    }

    @Test
    void testShouldValidateCorrectEmail() {
        assertThat(validator.isValid("test@mail.com", null)).isTrue();
        assertThat(validator.isValid("test@mail.com.br", null)).isTrue();
    }
}
