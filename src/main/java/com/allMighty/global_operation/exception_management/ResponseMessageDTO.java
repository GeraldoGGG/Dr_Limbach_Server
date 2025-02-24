package com.allMighty.global_operation.exception_management;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record ResponseMessageDTO(Integer index, String code, String key, String message) {
  }
