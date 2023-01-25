package com.proejct.ClassActionClaim.dto.RequestBody;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequest {

    String title;
    String content;
    Long week;
    Long lectureId;
    String studentId;

    public static BoardRequest of(Long lectureId, String studentId) {
        return BoardRequest.of(null, null, null, lectureId, studentId);
    }

    public static BoardRequest of(String title, String content, Long week, Long lectureId, String studentId) {
        return new BoardRequest(
                title,
                content,
                week,
                lectureId,
                studentId
        );
    }
}
