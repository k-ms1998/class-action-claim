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
    private String studentEmail;

    public StudentRequestDTO(String name, String password, String studentEmail) {
        this.name = name;
        this.password = password;
        this.studentEmail = studentEmail;
    }


    public static StudentRequestDTO of(String name, String password, String studentEmail) {
        return new StudentRequestDTO(name, password, studentEmail);
    }
}
