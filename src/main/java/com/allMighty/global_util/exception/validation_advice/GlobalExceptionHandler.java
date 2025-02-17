package com.allMighty.global_util.exception.validation_advice;


import com.allMighty.global_util.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String errorMessage = fieldErrors.get(0).getDefaultMessage();

        return ResponseEntity.badRequest().body(new ResponseError(errorMessage));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error(ex.toString());
        return ResponseEntity.badRequest().body(new ResponseError(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error(ex.toString());
        return ResponseEntity.badRequest().body(new ResponseError(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(ex.toString());
        return ResponseEntity.badRequest().body(new ResponseError(ex.getMessage()));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError("User with this role is not authorized!"), FORBIDDEN);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> notFoundException(NotFoundException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError(ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<?> badRequestException(BadRequestException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError(ex.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<?> conflictException(ConflictException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError(ex.getMessage()), CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError("Internal Server Error!"), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Object> objectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError("Version conflict for : " + ex.getPersistentClassName()), CONFLICT);
    }

    @ExceptionHandler(value = {InternalServerException.class})
    public ResponseEntity<?> internalServerException(InternalServerException ex) {
        log.error(ex.toString());
        return new ResponseEntity<>(new ResponseError(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }
}