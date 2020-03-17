package com.sandrocaseiro.apitemplate.exceptions;

public class InvalidTokenException extends BaseException {
    @Override
    public CustomErrors error() {
        return CustomErrors.INVALID_TOKEN_ERROR;
    }
}
