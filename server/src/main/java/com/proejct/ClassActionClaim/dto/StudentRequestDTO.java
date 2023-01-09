package com.proejct.ClassActionClaim.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentRequestDTO {

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    public StudentRequestDTO(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public static StudentRequestDTO of(String name, String password, String email) {
        return new StudentRequestDTO(name, password, email);
    }
}
