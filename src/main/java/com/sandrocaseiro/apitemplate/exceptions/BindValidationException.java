package com.sandrocaseiro.apitemplate.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
public class BindValidationException extends BaseException {
    @Getter
    private final Errors bindErrors;

    @Override
    public CustomErrors error() {
        return CustomErrors.BINDING_VALIDATION_ERROR;
    }
}
