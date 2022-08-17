package com.proejct.ClassActionClaim.dto.RequestBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthRequest {
    String email;
    String password;
    String name;
    String code;
}
