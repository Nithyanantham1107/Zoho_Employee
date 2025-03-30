package com.exception;

public class EmployeeTypeException extends Exception {

    public EmployeeTypeException(String message) {

        super(message);
    }

    public EmployeeTypeException(String message, Throwable cause) {

        super(message, cause);
    }

    public EmployeeTypeException(Throwable cause) {

        super(cause);
    }
}
