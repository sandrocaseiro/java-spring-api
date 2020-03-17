package com.sandrocaseiro.apitemplate.exceptions;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }

    public APIException(Throwable cause) {
        super(cause);
    }
}
