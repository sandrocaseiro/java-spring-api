package com.sandrocaseiro.apitemplate.exceptions;

public abstract class BaseException extends RuntimeException {
    public BaseException() { }

    public BaseException(Throwable ex) {
        super(ex);
    }

    public abstract CustomErrors error();
}
