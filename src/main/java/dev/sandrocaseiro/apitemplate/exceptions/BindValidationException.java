package dev.sandrocaseiro.apitemplate.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
public class BindValidationException extends BaseException {
    @Getter
    private final Errors bindErrors;

    @Override
    public AppErrors error() {
        return AppErrors.BINDING_VALIDATION_ERROR;
    }
}
