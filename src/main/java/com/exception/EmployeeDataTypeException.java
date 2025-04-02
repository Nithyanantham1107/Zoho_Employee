package com.exception;

public class EmployeeDataTypeException extends Exception {

    public EmployeeDataTypeException(String message) {

        super(message);
    }

    public EmployeeDataTypeException(String message, Throwable cause) {

        super(message, cause);
    }

    public EmployeeDataTypeException(Throwable cause) {

        super(cause);
    }
}
