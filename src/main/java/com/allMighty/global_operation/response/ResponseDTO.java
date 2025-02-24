package com.allMighty.global_operation.response;

import com.allMighty.global_operation.exception_management.PayloadValidationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ResponseDTO<T> {
    private PayloadValidationStatus validation = new PayloadValidationStatus();
}
