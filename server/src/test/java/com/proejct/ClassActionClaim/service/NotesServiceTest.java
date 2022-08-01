package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Notes;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.NotesRequestDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.NotesResponseDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.NotesRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotesServiceTest {

    @Autowired
    private NotesService notesService;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Test
    void givenNoteRequest_whenExecutingSaveNote_thenSuccess() {
        // Given
        Student student = new Student("userA", "passwordA", "test@sju.ac.kr");
        Student savedStudent = studentRepository.save(student);

        Lecture lecture = new Lecture("001", "LecA", "ProfA");
        Lecture savedLecture = lectureRepository.save(lecture);


        String title = "Title #1";
        String content = "Content #1";
        Integer week = 1;
        Long studentId = savedStudent.getId();
        Long lectureId = savedLecture.getId();

        NotesRequestDTO notesRequestDTO = new NotesRequestDTO(title, content, week, studentId, lectureId);

        // When
        NotesResponseDTO responseDTO = notesService.saveNote(notesRequestDTO);

        // Then
        /**
         * 1. Saved Note must have the title 'Title #1'
         * 2. Saved Note must have the content 'Content #1'
         * 3. Count of tuples in Notes should be 1
         */
        Assertions.assertThat(responseDTO.getTitle()).isEqualTo("Title #1");
        Assertions.assertThat(responseDTO.getContent()).isEqualTo("Content #1");

        List<Notes> allNotes = notesRepository.findAll();
        Assertions.assertThat(allNotes).hasSize(1);
    }

    @Test
    void givenNoteRequest_whenExecutingGetNotes_thenSuccess() {
        // Given
        Student student = new Student("userA", "passwordA", "test@sju.ac.kr");
        Student savedStudent = studentRepository.save(student);

        Lecture lecture = new Lecture("001", "LecA", "ProfA");
        Lecture savedLecture = lectureRepository.save(lecture);

        String title = "Title #1";
        String content = "Content #1";
        Integer week = 1;
        Long studentId = savedStudent.getId();
        Long lectureId = savedLecture.getId();

        NotesRequestDTO notesRequestDTOA = new NotesRequestDTO(title, content, week, studentId, lectureId);
        NotesRequestDTO notesRequestDTOB = new NotesRequestDTO(title, content, week, studentId, lectureId);

        notesService.saveNote(notesRequestDTOA);
        notesService.saveNote(notesRequestDTOB);

        // When
        ToClientResponse<List<NotesResponseDTO>> result = notesService.getNotesByWeek(1, new NotesRequestDTO(savedLecture.getId(), savedStudent.getId()));

        // Then
        String message = result.getMessage();
        Integer count = result.getCount();
        List<NotesResponseDTO> data = result.getData();

        Assertions.assertThat(message).isEqualTo("Fetched Notes");
        Assertions.assertThat(count).isEqualTo(2);
        Assertions.assertThat(data).hasSize(2);
    }

    @Test
    void givenNoteRequest_whenExecutingUpdateNote_thenSuccess() {
        // Given
        Student student = new Student("userA", "passwordA", "test@sju.ac.kr");
        Student savedStudent = studentRepository.save(student);

        Lecture lecture = new Lecture("001", "LecA", "ProfA");
        Lecture savedLecture = lectureRepository.save(lecture);


        String title = "Title #1";
        String content = "Content #1";
        Integer week = 1;
        Long studentId = savedStudent.getId();
        Long lectureId = savedLecture.getId();

        Notes note = new Notes(title, content, week , savedLecture, savedStudent);
        Notes savedNote = notesRepository.save(note);

        String updatedTitle = "Title #2";
        String updatedContent = "Content #2";
        NotesRequestDTO updateNotesRequestDTO = new NotesRequestDTO(updatedTitle, updatedContent, week, studentId, lectureId);

        // When
        ToClientResponse<NotesResponseDTO> notesResponseDTOToClientResponse = notesService.updateNote(savedNote.getId(), updateNotesRequestDTO);

        // Then
        String message = notesResponseDTOToClientResponse.getMessage();
        Assertions.assertThat(message).isEqualTo("Note Updated.");

        Integer count = notesResponseDTOToClientResponse.getCount();
        Assertions.assertThat(count).isEqualTo(1);

        NotesResponseDTO data = notesResponseDTOToClientResponse.getData();
        Assertions.assertThat(data.getTitle()).isEqualTo("Title #2");
        Assertions.assertThat(data.getContent()).isEqualTo("Content #2");

        /**
         * Notes 를 새로 추가한게 아니라 기존에 있던 것을 업데이트 한것이기 때문에 하나의 Notes 만 저장되어 있어야됨
         */
        List<Notes> allNotes = notesRepository.findAll();
        Assertions.assertThat(allNotes).hasSize(1);
    }

}