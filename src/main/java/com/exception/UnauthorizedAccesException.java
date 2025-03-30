package com.exception;

public class UnauthorizedAccesException extends Exception {

    public UnauthorizedAccesException(String message) {
        super(message);
    }
    public UnauthorizedAccesException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnauthorizedAccesException(Throwable cause) {
        super(cause);
    }

}
