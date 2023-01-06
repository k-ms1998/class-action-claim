package com.proejct.ClassActionClaim.domain;

import com.proejct.ClassActionClaim.domain.baseEntities.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * Student Entity
 *
 * id : AutoGenerated Id
 * username: Student Assigned Username; Unique
 * password
 * email: Must be an email ending with @sju.ac.kr; suffix for student emails attending Sejong University
 *
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends User {

    @Column(nullable = false)
    @Email
    private String email;

    public Student(String username, String password, String email) {
        super(username, password, "ROLE_STUDENT");
        this.email = email;
    }

    public static Student of(String username, String password, String email) {
        return new Student(username, password, email);
    }

}
