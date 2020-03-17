package com.sandrocaseiro.apitemplate.exceptions;

public class MethodNotAllowedException extends BaseException {
    @Override
    public CustomErrors error() {
        return CustomErrors.METHOD_NOT_ALLOWED_ERROR;
    }
}
