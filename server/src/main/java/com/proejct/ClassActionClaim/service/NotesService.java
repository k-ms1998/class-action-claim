package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Notes;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.NotesRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.NotesResponseDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.NotesRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final NotesRepository notesRepository;

    public ToClientResponse<List<NotesResponseDTO>> getNotesByWeek(Long week, NotesRequest request) {
        Long lectureId = request.getLectureId();
        String studentId = request.getStudentId();

        List<Notes> notesByWeek = notesRepository.findNotesByWeek(week, lectureId, studentId);
        List<NotesResponseDTO> notesResponseDTOS
                = notesByWeek.stream()
                .map(n -> {
                    String title = n.getTitle();
                    String content = n.getContent();
                    return new NotesResponseDTO(title, content);
                }).collect(Collectors.toList());


        return new ToClientResponse<List<NotesResponseDTO>>("Fetched Notes", notesResponseDTOS.size(), notesResponseDTOS);
    }

    public NotesResponseDTO saveNote(NotesRequest request) {
        Long week = request.getWeek();
        String title = request.getTitle();
        String content = request.getContent();

        Lecture lecture = getLecture(request.getLectureId());
        Student student = getStudent(request.getStudentId());

        Notes notes = new Notes(title, content, week, lecture, student);

        Notes savedNote = notesRepository.save(notes);
        NotesResponseDTO notesResponseDTO = new NotesResponseDTO(savedNote.getTitle(), savedNote.getContent());

        return notesResponseDTO;
    }

    public ToClientResponse<NotesResponseDTO> updateNote(Long noteId, NotesRequest requestDTO) {
        Optional<Notes> noteOptional = notesRepository.findById(noteId);
        // Check if note exists
        if (noteOptional == null) {
            return new ToClientResponse<NotesResponseDTO>("Error: Couldn't find note.", 0, null);
        }

        // Check if the studentId, lectureId and week are a match
        Notes note = noteOptional.get();
        if (note.getStudent().getUuid() != requestDTO.getStudentId()
                || note.getLecture().getId() != requestDTO.getLectureId() || note.getWeek() != requestDTO.getWeek()) {
            return new ToClientResponse<NotesResponseDTO>("Error: Owner and/or lecture doesn't match.", 0, null);
        }

        // Update Note
        String updatedTitle = requestDTO.getTitle();
        String updatedContent = requestDTO.getContent();
        note.updateTitle(updatedTitle);
        note.updateContent(updatedContent);

        // Save Updated Note
        Notes updatedNote = notesRepository.save(note);
        NotesResponseDTO updatedNoteDto = new NotesResponseDTO(updatedNote.getTitle(), updatedNote.getContent());

        return new ToClientResponse<NotesResponseDTO>("Note Updated.", 1, updatedNoteDto);
    }

    public ToClientResponse<NotesResponseDTO> removeNote(Long noteId, NotesRequest requestDTO) {
        Optional<Notes> noteOptional = notesRepository.findById(noteId);
        if (noteOptional == null) {
            return new ToClientResponse<NotesResponseDTO>("Error: Couldn't find note.", 0, null);
        }

        Notes note = noteOptional.get();
        if (note.getStudent().getUuid() != requestDTO.getStudentId()
                || note.getLecture().getId() != requestDTO.getLectureId() || note.getWeek() != requestDTO.getWeek()) {
            return new ToClientResponse<NotesResponseDTO>("Error: Owner and/or lecture doesn't match.", 0, null);
        }

        notesRepository.delete(note);
        NotesResponseDTO deletedNote = new NotesResponseDTO(note.getTitle(), note.getContent());

        return new ToClientResponse<NotesResponseDTO>("Note Removed.", 1, deletedNote);
    }

    protected Lecture getLecture(Long id) {
        return lectureRepository.findById(id).get();
    }

    protected Student getStudent(String uuid) {
        return studentRepository.findByUuid(uuid);
    }

}
