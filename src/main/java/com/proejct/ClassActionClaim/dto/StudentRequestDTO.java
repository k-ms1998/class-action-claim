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
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String studentEmail;

    public StudentRequestDTO(String username, String password, String studentEmail) {
        this.username = username;
        this.password = password;
        this.studentEmail = studentEmail;
    }


    public static StudentRequestDTO of(String username, String password, String studentEmail) {
        return new StudentRequestDTO(username, password, studentEmail);
    }
}
