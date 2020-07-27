package dev.sandrocaseiro.apitemplate.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppException extends BaseException {
    private final AppErrors errorType;

    private AppException(AppErrors errorType, Throwable ex) {
        super(ex);
        this.errorType = errorType;
    }

    @Override
    public AppErrors error() {
        return errorType;
    }

    public static AppException of(AppErrors errorType) {
        return new AppException(errorType);
    }

    public static AppException of(AppErrors errorType, Throwable ex) {
        return new AppException(errorType, ex);
    }
}
