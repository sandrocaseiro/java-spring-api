package com.sandrocaseiro.apitemplate.exceptions;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() { }

    public UnauthorizedException(Throwable ex) {
        super(ex);
    }

    @Override
    public CustomErrors error() {
        return CustomErrors.UNAUTHORIZED_ERROR;
    }
}
