package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToClientResponse<T> {

    public String message;
    public Integer count;
    public Integer status;
    public T data;

    public ToClientResponse(String message, Integer count, T data) {
        this.message = message;
        this.count = count;
        this.data = data;
    }

    public ToClientResponse(String message, Integer count, Integer status, T data) {
        this.message = message;
        this.count = count;
        this.status = status;
        this.data = data;
    }
}
