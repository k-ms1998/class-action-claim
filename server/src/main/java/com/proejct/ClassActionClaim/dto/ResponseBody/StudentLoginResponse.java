package com.proejct.ClassActionClaim.dto.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginResponse {

    @NotNull
    private String id;

    @NotNull
    private String email;

    private String message;

    public static StudentLoginResponse of(String id, String email, String message) {
        return new StudentLoginResponse(id, email, message);
    }
}
