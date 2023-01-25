package com.proejct.ClassActionClaim.dto.RequestBody.Board;

import com.proejct.ClassActionClaim.domain.Board;
import com.proejct.ClassActionClaim.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteRequest {

    String studentId;
    String title;
    String content;
    Long week;

    public static BoardWriteRequest of(String studentId, String title, String content) {
        return BoardWriteRequest.of(studentId, title, content, null);
    }

    public static BoardWriteRequest of(String studentId, String title, String content, Long week) {
        return new BoardWriteRequest(studentId, title, content, week);
    }

    public static Board toEntity(BoardWriteRequest request, Student student) {
        return Board.of(
                request.getTitle(),
                request.getContent(),
                request.getWeek(),
                student
        );
    }

    @Override
    public String toString() {
        return "BoardWriteRequest{" +
                "studentId='" + studentId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", week=" + week +
                '}';
    }
}
