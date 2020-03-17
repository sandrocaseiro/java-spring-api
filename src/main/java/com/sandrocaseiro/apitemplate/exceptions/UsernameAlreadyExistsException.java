package com.sandrocaseiro.apitemplate.exceptions;

public class UsernameAlreadyExistsException extends BaseException {
    @Override
    public CustomErrors error() {
        return CustomErrors.USERNAME_ALREADY_EXISTS;
    }
}
