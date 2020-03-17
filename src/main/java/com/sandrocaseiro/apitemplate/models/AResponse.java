package com.sandrocaseiro.apitemplate.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AResponse<T> {
    private boolean isSuccess;

    private List<Error> errors;

    private String message;

    private T data;

    @Data
    public static class Error {
        private int code;

        private ErrorType type;

        private String description;
    }

    @AllArgsConstructor
    @Getter
    public enum ErrorType {
        ERROR("E"),
        WARNING("W"),
        INFORMATION("I"),
        SUCCESS("S");

        @JsonValue
        private String value;
    }
}
