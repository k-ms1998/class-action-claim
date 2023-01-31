package com.proejct.ClassActionClaim.exception;

import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(ClassActionClaimException.class)
    public ToClientResponse<?> applicationHandler(ClassActionClaimException e) {
        log.error("Error occurred. {}", e.toString());

        return new ToClientResponse<Void>(
                e.getMessage(),
                0,
                e.getErrorCode().getStatus(),
                null);
    }

    @ExceptionHandler(RuntimeException.class)
    public ToClientResponse<?> applicationHandler(RuntimeException e) {
        log.error("Error occurred. {}", e.toString());

        return new ToClientResponse<Void>(
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                0,
                ErrorCode.INTERNAL_SERVER_ERROR.getStatus(),
                null);
    }
}
