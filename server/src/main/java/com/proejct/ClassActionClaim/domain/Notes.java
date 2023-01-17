package com.proejct.ClassActionClaim.domain;

import com.proejct.ClassActionClaim.domain.baseEntities.IdDateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notes extends IdDateTimeEntity {

    String title;
    String content;
    Long week;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Student student;

    public Notes(String title, String content, Long week, Lecture lecture, Student student) {

        this.title = title;
        this.content = content;
        this.week = week;
        this.lecture = lecture;
        this.student = student;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
