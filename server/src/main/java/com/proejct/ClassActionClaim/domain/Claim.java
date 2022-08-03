package com.proejct.ClassActionClaim.domain;

import com.proejct.ClassActionClaim.domain.baseEntities.IdEntity;
import com.proejct.ClassActionClaim.domain.enums.ClaimType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Claim extends IdEntity {

    String title;
    String content;
    Integer upVotes;

    @Enumerated(EnumType.STRING)
    ClaimType claimType;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    Lecture lecture;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    Student student;

    public Claim(String title, String content, ClaimType claimType, Lecture lecture, Student student) {
        this.title = title;
        this.content = content;
        this.upVotes = 0;
        this.claimType = claimType;
        this.lecture = lecture;
        this.student = student;
    }
}
