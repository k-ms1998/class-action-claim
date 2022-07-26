package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthResponse {

    String email;
    String authCode;

    public EmailAuthResponse(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return email + ":" + authCode;
    }
}
