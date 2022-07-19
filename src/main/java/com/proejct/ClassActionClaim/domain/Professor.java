package com.proejct.ClassActionClaim.domain;

import com.proejct.ClassActionClaim.domain.baseEntities.User;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Entity
@Getter
public class Professor extends User {

    @Column(nullable = false)
    @Email
    private String professorEmail;

    public Professor(String username, String password, String authority, String professorEmail) {
        super(username, password, authority);
        this.professorEmail = professorEmail;
    }
}

