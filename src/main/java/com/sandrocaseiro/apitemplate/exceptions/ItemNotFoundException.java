package com.sandrocaseiro.apitemplate.exceptions;

public class ItemNotFoundException extends BaseException {
    @Override
    public CustomErrors error() {
        return CustomErrors.ITEM_NOT_FOUND_ERROR;
    }
}
