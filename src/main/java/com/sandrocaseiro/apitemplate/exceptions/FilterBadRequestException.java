package com.sandrocaseiro.apitemplate.exceptions;

import lombok.Getter;

public class FilterBadRequestException extends BaseException {
    @Getter
    private final String fieldName;

    public FilterBadRequestException(String fieldName) {
        this.fieldName = fieldName;
    }

    public FilterBadRequestException(String fieldName, Throwable ex) {
        super(ex);
        this.fieldName = fieldName;
    }

    @Override
    public CustomErrors error() {
        return CustomErrors.FILTER_REQUEST_ERROR;
    }
}
