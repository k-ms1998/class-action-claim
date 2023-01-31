package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToClientResponse<T> {

    public String message;
    public Integer count;
    public HttpStatus status;
    public T data;

    public ToClientResponse(String message, Integer count, T data) {
        this.message = message;
        this.count = count;
        this.data = data;
    }

    public ToClientResponse(String message, Integer count, HttpStatus status, T data) {
        this.message = message;
        this.count = count;
        this.status = status;
        this.data = data;
    }
}
