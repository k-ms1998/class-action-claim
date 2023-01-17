package com.proejct.ClassActionClaim.dto.RequestBody;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotesRequest {

    String title;
    String content;
    Long week;
    Long lectureId;
    String studentId;

    public static NotesRequest of(Long lectureId, String studentId) {
        return NotesRequest.of(null, null, null, lectureId, studentId);
    }

    public static NotesRequest of(String title, String content, Long week, Long lectureId, String studentId) {
        return new NotesRequest(
                title,
                content,
                week,
                lectureId,
                studentId
        );
    }
}
