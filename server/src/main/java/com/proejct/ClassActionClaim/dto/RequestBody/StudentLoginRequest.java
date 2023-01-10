package com.proejct.ClassActionClaim.dto.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public static StudentLoginRequest of(String email, String password) {
        return new StudentLoginRequest(email, password);
    }
}
