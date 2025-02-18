package com.allMighty.global_operation.exception_management.exception;

import org.springframework.http.HttpStatus;

/**
 * Abstract class for errors resulting in 5XX status codes.
 * Note: If a message is given, it will be visible to the end user.
 *
 * @since 19-Apr-2021
 */
public abstract class Http5xxException extends RuntimeException {

  Http5xxException() {
    super();
  }

  Http5xxException(String message) {
    super(message);
  }

  Http5xxException(Throwable cause) {
    // We use this constructor to prevent e.getMessage() returning the message of the cause, so that
    // it is not shared to clients
    super(null, cause);
  }

  Http5xxException(String message, Throwable cause) {
    super(message, cause);
  }

  public abstract HttpStatus getStatusCode();
}
