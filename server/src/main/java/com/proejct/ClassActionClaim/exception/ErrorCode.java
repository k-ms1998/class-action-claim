package com.proejct.ClassActionClaim.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    EMPTY_PARAMETERS(HttpStatus.BAD_REQUEST, "Check parameters."),
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT, "Username already exists."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "Email already exists."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "User doesn't exists."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "User already exists."),
    UNVERIFIED_USER(HttpStatus.UNAUTHORIZED, "User Unverified."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "Check password."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");

    private HttpStatus status;
    private String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
