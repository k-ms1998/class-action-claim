package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Notes;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.NotesRequestDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.NotesResponseDTO;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.NotesRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final NotesRepository notesRepository;

    public NotesResponseDTO saveNote(NotesRequestDTO requestDTO) {
        Integer week = requestDTO.getWeek();
        String title = requestDTO.getTitle();
        String content = requestDTO.getContent();

        Lecture lecture = getLecture(requestDTO.getLectureId());
        Student student = getStudent(requestDTO.getStudentId());

        Notes notes = new Notes(title, content, week, lecture, student);

        Notes savedNote = notesRepository.save(notes);
        NotesResponseDTO notesResponseDTO = new NotesResponseDTO(savedNote.getTitle(), savedNote.getContent());

        return notesResponseDTO;
    }

    protected Lecture getLecture(Long id) {
        return lectureRepository.findById(id).get();
    }

    protected Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }
}
