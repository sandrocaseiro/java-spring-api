package dev.sandrocaseiro.apitemplate.clients.retryers;

import feign.RetryableException;
import feign.Retryer;

public class NoRetryer implements Retryer {
    @Override
    public void continueOrPropagate(RetryableException e) {
        throw e;
    }

    @Override
    public Retryer clone() {
        return this;
    }
}
