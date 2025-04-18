package com.allMighty.global_operation.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteDTO {
    boolean success = true;
    String message;
    Long id;
}
