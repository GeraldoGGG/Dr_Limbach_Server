package com.allMighty.global_operation.exception_management.exception;

import org.springframework.http.HttpStatus;

/**
 * Abstract class for errors resulting in 4XX status codes.
 */
public abstract class Http4xxException extends RuntimeException {

  Http4xxException() {
    super();
  }

  Http4xxException(String message) {
    super(message);
  }

  public abstract HttpStatus getStatusCode();
}
