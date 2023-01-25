package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.config.BaseConfig;
import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Board;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.Board.BoardWriteRequest;
import com.proejct.ClassActionClaim.dto.RequestBody.BoardRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.BoardResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.BoardRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import com.proejct.ClassActionClaim.service.server.EmailAuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private LectureRepository lectureRepository;

    @Test
    void givenBoardWriteRequest_whenExecutingWriteNote_thenSuccess() {
        // Given
        String studentId = "uuid#1";
        String title = "Title #1";
        String content = "Content #1";
        Student student = createStudent(studentId);
        given(studentRepository.findByUuid(studentId)).willReturn(student);

        BoardWriteRequest boardRequest = BoardWriteRequest.of(studentId, title, content);

        // When
        BoardResponse result = boardService.writeBoard(boardRequest);

        // Then
        Assertions.assertThat(result.getTitle()).isEqualTo(title);
        Assertions.assertThat(result.getContent()).isEqualTo(content);
        then(studentRepository).should().findByUuid(studentId);
    }

    @Test
    void givenNoteRequest_whenExecutingGetNotes_thenSuccess() {
        // Given
        Student student = Student.of("userA", "passwordA", "test@sju.ac.kr");
        Student savedStudent = studentRepository.save(student);

        Lecture lecture = new Lecture("001", "LecA", "ProfA");
        Lecture savedLecture = lectureRepository.save(lecture);

        String title = "Title #1";
        String content = "Content #1";
        Long week = 1L;
        String studentId = savedStudent.getUuid();
        Long lectureId = savedLecture.getId();

       BoardWriteRequest boardRequestDTOA = BoardWriteRequest.of(studentId, title, content);
       BoardWriteRequest boardRequestDTOB = BoardWriteRequest.of(studentId, title, content);

        boardService.writeBoard(boardRequestDTOA);
        boardService.writeBoard(boardRequestDTOB);

        // When
        ToClientResponse<List<BoardResponse>> result = boardService.getNotesByWeek(1L, BoardRequest.of(savedLecture.getId(), savedStudent.getUuid()));

        // Then
        String message = result.getMessage();
        Integer count = result.getCount();
        List<BoardResponse> data = result.getData();

        Assertions.assertThat(message).isEqualTo("Fetched Notes");
        Assertions.assertThat(count).isEqualTo(2);
        Assertions.assertThat(data).hasSize(2);
    }

    @Test
    void givenNoteRequest_whenExecutingUpdateNote_thenSuccess() {
        // Given
        Student student = Student.of("userA", "passwordA", "test@sju.ac.kr");
        Student savedStudent = studentRepository.save(student);

        Lecture lecture = new Lecture("001", "LecA", "ProfA");
        Lecture savedLecture = lectureRepository.save(lecture);


        String title = "Title #1";
        String content = "Content #1";
        Long week = 1L;
        String studentId = savedStudent.getUuid();
        Long lectureId = savedLecture.getId();

        Board note = new Board(title, content, week, savedStudent);
        Board savedNote = boardRepository.save(note);

        String updatedTitle = "Title #2";
        String updatedContent = "Content #2";
        BoardRequest updateBoardRequest = new BoardRequest(updatedTitle, updatedContent, week, lectureId, studentId);

        // When
        ToClientResponse<BoardResponse> notesResponseDTOToClientResponse = boardService.updateNote(savedNote.getId(), updateBoardRequest);

        // Then
        String message = notesResponseDTOToClientResponse.getMessage();
        Assertions.assertThat(message).isEqualTo("Note Updated.");

        Integer count = notesResponseDTOToClientResponse.getCount();
        Assertions.assertThat(count).isEqualTo(1);

        BoardResponse data = notesResponseDTOToClientResponse.getData();
        Assertions.assertThat(data.getTitle()).isEqualTo("Title #2");
        Assertions.assertThat(data.getContent()).isEqualTo("Content #2");

        /**
         * Notes 를 새로 추가한게 아니라 기존에 있던 것을 업데이트 한것이기 때문에 하나의 Notes 만 저장되어 있어야됨
         */
        List<Board> allNotes = boardRepository.findAll();
        Assertions.assertThat(allNotes).hasSize(1);
    }

    @Test
    void givenNoteRequest_whenExecutingDeleteNote_thenSuccess() {
        // Given
        Student student = Student.of("userA", "passwordA", "test@sju.ac.kr");
        Student savedStudent = studentRepository.save(student);

        Lecture lecture = new Lecture("001", "LecA", "ProfA");
        Lecture savedLecture = lectureRepository.save(lecture);

        String title = "Title #1";
        String content = "Content #1";
        Long week = 1L;
        String studentId = savedStudent.getUuid();
        Long lectureId = savedLecture.getId();

        Board note = new Board(title, content, week, savedStudent);
        Board savedNote = boardRepository.save(note);

        BoardRequest boardRequest = new BoardRequest(title, content, week, lectureId, studentId);

        // When
        ToClientResponse<BoardResponse> notesResponseDTOToClientResponse = boardService.removeNote(savedNote.getId(), boardRequest);

        // Then
        String message = notesResponseDTOToClientResponse.getMessage();
        Assertions.assertThat(message).isEqualTo("Note Removed.");

        Integer count = notesResponseDTOToClientResponse.getCount();
        Assertions.assertThat(count).isEqualTo(1);

        BoardResponse data = notesResponseDTOToClientResponse.getData();
        String removedTitle = data.getTitle();
        String removedContent = data.getContent();
        Assertions.assertThat(removedTitle).isEqualTo("Title #1");
        Assertions.assertThat(removedContent).isEqualTo("Content #1");

        List<Board> allNotes = boardRepository.findAll();
        Assertions.assertThat(allNotes).hasSize(0);
    }

    private Student createStudent(String studentId) {
        return Student.of(studentId, "kms", "password", "test@sju.com", true);
    }
}