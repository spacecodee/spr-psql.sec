package com.spacecodee.sprpsqlsec.exceptions;

public class NoDisabledException extends RuntimeException {

    public NoDisabledException() {
    }

    public NoDisabledException(String message) {
        super(message);
    }

    public NoDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}
