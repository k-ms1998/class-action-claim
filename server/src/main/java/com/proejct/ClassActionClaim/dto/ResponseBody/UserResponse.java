package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse<T> {

    public String message;
    public T data;

    public UserResponse(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" + this.getMessage() + " }";
    }
}
