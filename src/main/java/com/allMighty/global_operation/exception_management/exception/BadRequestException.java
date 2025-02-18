package com.allMighty.global_operation.exception_management.exception;

import com.allMighty.global_operation.response.PayloadValidationStatus;
import org.springframework.http.HttpStatus;

public class BadRequestException extends Http4xxException {

    private PayloadValidationStatus validationStatus;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, PayloadValidationStatus validationStatus) {
        super(message);
        this.validationStatus = validationStatus;
    }

    public PayloadValidationStatus getValidationStatus() {
        return validationStatus;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
