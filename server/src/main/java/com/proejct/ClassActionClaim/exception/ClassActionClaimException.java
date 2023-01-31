package com.proejct.ClassActionClaim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClassActionClaimException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    @Override
    public String getMessage() {
        if (message == null || message.isBlank()) {
            return errorCode.getMessage();
        }
        return String.format("%s. %s", errorCode.getMessage(), this.message);
    }
}
