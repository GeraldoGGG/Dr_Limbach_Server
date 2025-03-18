package com.allMighty.global_operation.exception_management.exception;

public class ExcelFailException extends RuntimeException {
    public ExcelFailException(String message) {
        super(message);
    }

    public ExcelFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
