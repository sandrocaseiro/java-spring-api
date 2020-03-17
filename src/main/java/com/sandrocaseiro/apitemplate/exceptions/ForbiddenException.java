package com.sandrocaseiro.apitemplate.exceptions;

public class ForbiddenException extends BaseException {
    @Override
    public CustomErrors error() {
        return CustomErrors.FORBIDDEN_ERROR;
    }
}
