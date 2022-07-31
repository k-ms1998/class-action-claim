package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Notes;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.NotesRequestDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.NotesResponseDTO;
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

}