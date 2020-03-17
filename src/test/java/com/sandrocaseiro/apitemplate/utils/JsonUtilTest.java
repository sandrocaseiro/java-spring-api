package com.sandrocaseiro.apitemplate.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilTest {
    @Test
    void testSerializeNullModelShouldReturnNull() {
        TestModel model = null;
        assertThat(JsonUtil.serialize(model)).isNull();
    }

    @Test
    void testPrettySerializeNullModelShouldReturnNull() {
        TestModel model = null;
        assertThat(JsonUtil.serializePrettyPrint(model)).isNull();
    }

    @Test
    void testSerializeModelShouldSerialize() {
        TestModel model = new TestModel("User", "user@mail.com");

        assertThat(JsonUtil.serialize(model)).isEqualTo("{\"name\":\"User\",\"email\":\"user@mail.com\"}");
    }

    @Test
    void testPrettySerializeModelShouldSerialize() {
        TestModel model = new TestModel("User", "user@mail.com");

        assertThat(JsonUtil.serializePrettyPrint(model)).isEqualTo("{\r\n  \"name\" : \"User\",\r\n  \"email\" : \"user@mail.com\"\r\n}");
    }

    @AllArgsConstructor
    @Data
    private static class TestModel {
        private String name;
        private String email;
    }
}
