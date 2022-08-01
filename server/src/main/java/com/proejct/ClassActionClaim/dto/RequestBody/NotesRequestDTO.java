package com.proejct.ClassActionClaim.dto.RequestBody;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotesRequestDTO {

    String title;
    String content;
    Integer week;
    Long lectureId;
    Long studentId;

    public NotesRequestDTO(Long lectureId, Long studentId) {
        this.lectureId = lectureId;
        this.studentId = studentId;
    }

    public NotesRequestDTO(String title, String content, Integer week, Long lectureId, Long studentId) {
        this.title = title;
        this.content = content;
        this.week = week;
        this.lectureId = lectureId;
        this.studentId = studentId;
    }
}
