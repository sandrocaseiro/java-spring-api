package dev.sandrocaseiro.apitemplate.validations;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class NotEmptyValidatorTest {
    NotEmptyValidator validator = new NotEmptyValidator();

    @Test
    void testNullIsNotValid() {
        assertThat(validator.isValid(null, null)).isFalse();
    }

    @Test
    void testEmptyIsNotValid() {
        assertThat(validator.isValid(Collections.emptyList(), null)).isFalse();
    }

    @Test
    void testListIsValid() {
        assertThat(validator.isValid(Collections.singletonList(1), null)).isTrue();
        assertThat(validator.isValid(Arrays.asList(1, 2, 3), null)).isTrue();
    }
}
