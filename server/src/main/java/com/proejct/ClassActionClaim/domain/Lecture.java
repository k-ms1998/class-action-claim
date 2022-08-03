package com.proejct.ClassActionClaim.domain;

import com.proejct.ClassActionClaim.domain.baseEntities.IdEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends IdEntity {

    @Column(name = "lecture_number")
    String lectureNumber;

    @Column(name = "lecture_name")
    String lectureName;

    String professor;

    public Lecture(String lectureNumber, String lectureName, String professor) {
        this.lectureNumber = lectureNumber;
        this.lectureName = lectureName;
        this.professor = professor;
    }
}
