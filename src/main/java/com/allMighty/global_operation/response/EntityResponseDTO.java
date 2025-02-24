package com.allMighty.global_operation.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityResponseDTO<T> extends ResponseDTO<T> {
    private T result;
}
