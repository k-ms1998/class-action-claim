package com.proejct.ClassActionClaim.domain;

import com.proejct.ClassActionClaim.domain.baseEntities.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String authority;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private boolean authenticated;

    public static Student of(String username, String password, String email) {
        return new Student(null, null, username, password, "ROLE_STUDENT", email, false);
    }

    public static Student of(String uuid, String username,String password, String email, boolean authenticated) {
        return new Student(null, uuid, username, password, "ROLE_STUDENT", email, authenticated);
    }
}
