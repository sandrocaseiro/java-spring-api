package dev.sandrocaseiro.template.exceptions;

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
    public AppErrors error() {
        return AppErrors.FILTER_REQUEST_ERROR;
    }
}
