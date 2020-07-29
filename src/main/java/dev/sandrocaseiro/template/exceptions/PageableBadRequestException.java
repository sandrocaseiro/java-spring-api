package dev.sandrocaseiro.template.exceptions;

import lombok.Getter;

public class PageableBadRequestException extends BaseException {
    @Getter
    private final String fieldName;

    public PageableBadRequestException(String fieldName) {
        this.fieldName = fieldName;
    }

    public PageableBadRequestException(String fieldName, Throwable ex) {
        super(ex);
        this.fieldName = fieldName;
    }

    @Override
    public AppErrors error() {
        return AppErrors.PAGEABLE_REQUEST_ERROR;
    }
}
