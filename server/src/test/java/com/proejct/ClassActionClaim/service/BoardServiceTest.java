package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Board;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.BoardRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.BoardResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.BoardRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Test
    void givenNoteRequest_whenExecutingSaveNote_thenSuccess() {
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

        BoardRequest boardRequest = new BoardRequest(title, content, week, lectureId, studentId);

        // When
        BoardResponse responseDTO = boardService.saveNote(boardRequest);

        // Then
        /**
         * 1. Saved Note must have the title 'Title #1'
         * 2. Saved Note must have the content 'Content #1'
         * 3. Count of tuples in Notes should be 1
         */
        Assertions.assertThat(responseDTO.getTitle()).isEqualTo("Title #1");
        Assertions.assertThat(responseDTO.getContent()).isEqualTo("Content #1");

        List<Board> allNotes = boardRepository.findAll();
        Assertions.assertThat(allNotes).hasSize(1);
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

        BoardRequest boardRequestDTOA = BoardRequest.of(title, content, week, lectureId, studentId);
        BoardRequest boardRequestDTOB = BoardRequest.of(title, content, week, lectureId, studentId);

        boardService.saveNote(boardRequestDTOA);
        boardService.saveNote(boardRequestDTOB);

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

        Board note = new Board(title, content, week , savedLecture, savedStudent);
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

        Board note = new Board(title, content, week , savedLecture, savedStudent);
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

}