package com.allMighty.global_operation.exception_management.validation_advice;

import com.allMighty.global_operation.exception_management.exception.BadRequestForEnumeration;
import org.springframework.security.authentication.*;

import java.util.HashMap;
import java.util.Map;

class GlobalExceptionHandlerData {
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
