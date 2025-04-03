package com.allMighty.global_operation.exception_management.exception;

import org.springframework.http.HttpStatus;

public class ExcelFailException extends Http5xxException {
    
    public ExcelFailException(String message) {
        super(message);
    }

    public ExcelFailException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
