package com.exception;

public class DBOperationException extends Throwable {
    public DBOperationException(String message) {
        super(message);
    }

    public DBOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBOperationException(Throwable cause) {
        super(cause);
    }
}
