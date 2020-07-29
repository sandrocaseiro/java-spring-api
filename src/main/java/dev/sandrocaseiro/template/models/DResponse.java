package dev.sandrocaseiro.template.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public class DResponse<T> {

    @Schema(description = "List of the operations results")
    private List<Error> errors;

    @Schema(description = "Endpoint response data", nullable = true)
    private T data;

    @JsonGetter("isSuccess")
    @Schema(description = "Operation was successful")
    private boolean isSuccess() {
        return errors == null || errors.stream().noneMatch(e -> e.type != ErrorType.SUCCESS);
    }

    public static <T> DResponse<T> ok(T data, int code, String message) {
        return new DResponse<>(Collections.singletonList(Error.success(code, message)), data);
    }

    public static DResponse notOk(int code, String message) {
        return new DResponse<>(Collections.singletonList(Error.error(code, message)), null);
    }

    public static DResponse notOk(List<Error> errors) {
        return new DResponse<>(errors, null);
    }

    @AllArgsConstructor
    @Getter
    public static class Error {
        @Schema(description = "Code of the operation result", example = "500")
        private int code;

        @Schema(description = "Type of the operation result")
        private ErrorType type;

        @Schema(description = "Description of the operation result", example = "Server error")
        private String description;

        public static Error error(int code, String description) {
            return new Error(code, ErrorType.ERROR, description);
        }

        public static Error warning(int code, String description) {
            return new Error(code, ErrorType.WARNING, description);
        }

        public static Error information(int code, String description) {
            return new Error(code, ErrorType.INFORMATION, description);
        }

        public static Error success(int code, String description) {
            return new Error(code, ErrorType.SUCCESS, description);
        }
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
