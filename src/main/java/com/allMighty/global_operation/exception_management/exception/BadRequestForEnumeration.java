package com.allMighty.global_operation.exception_management.exception;

import org.springframework.http.HttpStatus;

public class BadRequestForEnumeration extends Http4xxException {
  Class enumClass;
  String source;

  public <E extends Enum<E>> BadRequestForEnumeration(String source, Class<E> enumClass) {
    this.enumClass = enumClass;
    this.source = source;
  }

  public HttpStatus getStatusCode() {
    return HttpStatus.BAD_REQUEST;
  }

  public Class getEnumClass() {
    return enumClass;
  }

  public String getSource() {
    return source;
  }
}
