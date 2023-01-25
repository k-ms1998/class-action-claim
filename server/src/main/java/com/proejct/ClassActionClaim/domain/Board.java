package com.proejct.ClassActionClaim.domain;

import com.proejct.ClassActionClaim.domain.baseEntities.IdDateTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board extends IdDateTimeEntity {

    String title;
    String content;
    Long week;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Student student;

    public static Board of(String title, String content, Long week, Student student) {
        return Board.of(title, content, week, new Lecture(), student);
    }

    public static Board of(String title, String content, Long week, Lecture lecture, Student student) {
        return new Board(title, content, week, student);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
