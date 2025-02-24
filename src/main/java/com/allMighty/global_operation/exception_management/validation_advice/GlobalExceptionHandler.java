package com.allMighty.global_operation.exception_management.validation_advice;


import com.allMighty.global_operation.exception_management.exception.*;
import com.allMighty.global_operation.exception_management.PayloadValidationStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.allMighty.global_operation.exception_management.validation_advice.GlobalExceptionHandler.GlobalExceptionHandlerData.*;
import static org.springframework.http.HttpStatus.*;

//TODO use the new format with 4 ttributes to handle exceptions


@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMessage = fieldErrors.get(0).getDefaultMessage();

        return logExceptionAndCreateResponse(Level.INFO, errorMessage, HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Handles errors in case of an invalid Enumeration, it displays all the values of the enumeration.
     */
    @ExceptionHandler(BadRequestForEnumeration.class)
    public ResponseEntity<Object> handleBadRequestForEnumeration(BadRequestForEnumeration ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message = ex.getEnumClass() != null
                ? DEFAULT_BAD_REQUEST_FOR_ENUMERATION + ": " + ex.getSource() + ",  available values  for enumeration "
                + ex.getEnumClass().getSimpleName() + " are: "
                + EnumSet.allOf(ex.getEnumClass()).stream().map(a -> a.toString()).collect(Collectors.joining(", "))
                : DEFAULT_BAD_REQUEST_FOR_ENUMERATION;
        return logExceptionAndCreateResponse(Level.INFO, message, status, ex);
    }


    /**
     * Handles errors during authentication caused by the user
     */
    @ExceptionHandler(
            {BadCredentialsException.class, CredentialsExpiredException.class,
                    AuthenticationCredentialsNotFoundException.class}
    )
    public ResponseEntity<Object> handleUserAuthenticationErrors(AuthenticationException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = ex.getMessage() != null ? String.format("User authentication error: %s", ex.getMessage())
                : defaultErrorMessageMap.get(ex.getClass());
        return logExceptionAndCreateResponse(Level.INFO, message, status, ex);
    }

    /**
     * Handles authentication exceptions that are likely to be because of an application error, rather than an en-user
     * error.
     */
    @ExceptionHandler
    public ResponseEntity<Object> handleAuthenticationError(AuthenticationException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        String message = ex.getMessage();
        if (message == null) {
            message = defaultErrorMessageMap.get(ex.getClass());
        }
        if (message == null) {
            message = DEFAULT_AUTHENTICATION_MESSAGE;
        }
        return logExceptionAndCreateResponse(Level.ERROR, message, status, ex);
    }

    /**
     * Handles subclasses of {@link Http4xxException}.
     * <p>
     * The response will have the status code as returned by the exception, with a body consisting of the exception
     * message, if any.
     * <p>
     * Errors will not be logged, as they are due to errors by the caller.
     */
    @ExceptionHandler
    public ResponseEntity<Object> handle4xxClientError(Http4xxException e) {
        HttpStatus status = e.getStatusCode();
        String message = e.getMessage() != null ? status + ": " + e.getMessage() : DEFAULT_4XX_MESSAGE;

        if (!e.getStatusCode().is4xxClientError()) {
            log.error("Invalid status code '" + status + "' supplied for client error", e);
        }

        return logExceptionAndCreateResponse(Level.INFO, message, status, e);
    }

    /**
     * Handles subclasses of {@link Http5xxException}.
     * <p>
     * The response will have the status code as returned by the exception, with a body consisting of the exception
     * message, if any.
     * <p>
     * Errors will be logged at the error level.
     */
    @ExceptionHandler
    public ResponseEntity<Object> handle5xxServerError(Http5xxException e) {
        HttpStatus status = e.getStatusCode();
        String message = e.getMessage() != null ? status + ": " + e.getMessage() : DEFAULT_5XX_MESSAGE;

        if (!e.getStatusCode().is5xxServerError()) {
            log.error("Invalid status code '" + status + "' supplied for server error", e);
        }

        return logExceptionAndCreateResponse(Level.ERROR, message, status, e);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.INFO, ex.getMessage(), BAD_REQUEST, ex);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.INFO, ex.getMessage(), BAD_REQUEST, ex);

    }


    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.ERROR, "User with this role is not authorized!", FORBIDDEN, ex);

    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.ERROR, ex.getMessage(), NOT_FOUND, ex);

    }


    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<?> conflictException(ConflictException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.INFO, ex.getMessage(), CONFLICT, ex);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.ERROR, ex.getMessage(), INTERNAL_SERVER_ERROR, ex);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.ERROR, ex.getMessage(), INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Object> objectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {
        log.error(ex.toString());
        return logExceptionAndCreateResponse(Level.INFO, "Version conflict for : " + ex.getPersistentClassName(), CONFLICT, ex);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return logExceptionAndCreateResponse(Level.ERROR, DEFAULT_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<Object> logExceptionAndCreateResponse(Level logLevel, String message, HttpStatus httpStatus,
                                                                 Exception exception) {
        String mark = Long.toHexString(System.currentTimeMillis());
        String formattedMessage = String.format("(%s) %s", mark, message);

        if (logLevel.isLessSpecificThan(Level.WARN)) {
            // INFO, DEBUG, TRACE, ALL will not include the stack trace, as they are not errors
            log.warn(formattedMessage);
        } else {
            log.error(formattedMessage, exception);
        }

        HttpHeaders headers = new HttpHeaders();
        if (exception instanceof BadRequestException && ((BadRequestException) exception).getValidationStatus() != null) {
            PayloadValidationStatus errorResponse = ((BadRequestException) exception).getValidationStatus();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(errorResponse, headers, httpStatus);
        }

        return new ResponseEntity<>(formattedMessage, headers, httpStatus);
    }

   static class GlobalExceptionHandlerData {
        static final Map<Class, String> defaultErrorMessageMap = new HashMap<>();

        static final String DEFAULT_AUTHENTICATION_MESSAGE = "An error occurred during authentication";
        static final String DEFAULT_4XX_MESSAGE = "The request is invalid";
        static final String DEFAULT_5XX_MESSAGE = "An internal error occurred, the request can not be processed at this time";
        static final String DEFAULT_CONSTRAINT_VIOLATION_MESSAGE = "The parameters passed are invalid";
        static final String DEFAULT_INTERNAL_SERVER_ERROR = "Internal server error";
        static final String DEFAULT_9XX_MESSAGE = "Custom error occurred, the request can not be processed at this time";
        static final String DEFAULT_BAD_REQUEST_FOR_ENUMERATION = "Invalid enumeration value";

        static {
            defaultErrorMessageMap.put(BadCredentialsException.class, "Incorrect credentials or missing schema");
            defaultErrorMessageMap.put(CredentialsExpiredException.class, "The supplied credentials have expired");
            defaultErrorMessageMap.put(AuthenticationCredentialsNotFoundException.class, "No credentials found");
            defaultErrorMessageMap.put(InternalAuthenticationServiceException.class,
                    "An internal error occurred, please try again later");
            defaultErrorMessageMap.put(AuthenticationServiceException.class,
                    "Problem authenticating the request, please try again later");
            defaultErrorMessageMap.put(BadRequestForEnumeration.class, DEFAULT_BAD_REQUEST_FOR_ENUMERATION);
        }

    }

}