package com.sandrocaseiro.apitemplate.exceptions;

public class NotFoundException extends BaseException {
    @Override
    public CustomErrors error() {
        return CustomErrors.NOT_FOUND_ERROR;
    }
}
