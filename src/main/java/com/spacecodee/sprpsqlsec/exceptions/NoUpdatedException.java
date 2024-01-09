package com.spacecodee.sprpsqlsec.exceptions;

public class NoUpdatedException extends RuntimeException {

    public NoUpdatedException() {
    }

    public NoUpdatedException(String message) {
        super(message);
    }

    public NoUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
