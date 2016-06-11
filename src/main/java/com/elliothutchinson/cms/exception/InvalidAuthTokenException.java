package com.elliothutchinson.cms.exception;

public class InvalidAuthTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidAuthTokenException() {
        super("Invalid authentication token");
    }
}
