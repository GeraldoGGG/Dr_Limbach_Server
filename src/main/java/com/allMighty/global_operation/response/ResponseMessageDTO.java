package com.allMighty.global_operation.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseMessageDTO {
  private final Integer index;
  private final String code;
  private final String key;
  private final String message;
}
