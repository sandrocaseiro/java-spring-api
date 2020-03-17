package com.sandrocaseiro.apitemplate.exceptions;

public class UnsupportedMediaTypeException extends BaseException {
    @Override
    public CustomErrors error() {
        return CustomErrors.UNSUPPORTED_MEDIA_TYPE;
    }
}
