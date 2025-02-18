package com.allMighty.global_operation.exception_management.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends Http5xxException  {

    public InternalServerException() {
        super();
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}