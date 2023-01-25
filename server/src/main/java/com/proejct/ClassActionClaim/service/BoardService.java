package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Board;
import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.BoardRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.BoardResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.repository.LectureRepository;
import com.proejct.ClassActionClaim.repository.BoardRepository;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;
    private final BoardRepository boardRepository;

    public ToClientResponse<List<BoardResponse>> getNotesByWeek(Long week, BoardRequest request) {
        Long lectureId = request.getLectureId();
        String studentId = request.getStudentId();

        List<Board> boardByWeek = boardRepository.findNotesByWeek(week, lectureId, studentId);
        List<BoardResponse> boardResponses
                = boardByWeek.stream()
                .map(n -> {
                    String title = n.getTitle();
                    String content = n.getContent();
                    return new BoardResponse(title, content);
                }).collect(Collectors.toList());


        return new ToClientResponse<List<BoardResponse>>("Fetched Notes", boardResponses.size(), boardResponses);
    }

    public BoardResponse saveNote(BoardRequest request) {
        Long week = request.getWeek();
        String title = request.getTitle();
        String content = request.getContent();

        Lecture lecture = getLecture(request.getLectureId());
        Student student = getStudent(request.getStudentId());

        Board board = new Board(title, content, week, lecture, student);

        Board savedNote = boardRepository.save(board);
        BoardResponse boardResponse = new BoardResponse(savedNote.getTitle(), savedNote.getContent());

        return boardResponse;
    }

    public ToClientResponse<BoardResponse> updateNote(Long noteId, BoardRequest requestDTO) {
        Optional<Board> noteOptional = boardRepository.findById(noteId);
        // Check if note exists
        if (noteOptional == null) {
            return new ToClientResponse<BoardResponse>("Error: Couldn't find note.", 0, null);
        }

        // Check if the studentId, lectureId and week are a match
        Board note = noteOptional.get();
        if (note.getStudent().getUuid() != requestDTO.getStudentId()
                || note.getLecture().getId() != requestDTO.getLectureId() || note.getWeek() != requestDTO.getWeek()) {
            return new ToClientResponse<BoardResponse>("Error: Owner and/or lecture doesn't match.", 0, null);
        }

        // Update Note
        String updatedTitle = requestDTO.getTitle();
        String updatedContent = requestDTO.getContent();
        note.updateTitle(updatedTitle);
        note.updateContent(updatedContent);

        // Save Updated Note
        Board updatedNote = boardRepository.save(note);
        BoardResponse updatedNoteDto = new BoardResponse(updatedNote.getTitle(), updatedNote.getContent());

        return new ToClientResponse<BoardResponse>("Note Updated.", 1, updatedNoteDto);
    }

    public ToClientResponse<BoardResponse> removeNote(Long noteId, BoardRequest requestDTO) {
        Optional<Board> noteOptional = boardRepository.findById(noteId);
        if (noteOptional == null) {
            return new ToClientResponse<BoardResponse>("Error: Couldn't find note.", 0, null);
        }

        Board note = noteOptional.get();
        if (note.getStudent().getUuid() != requestDTO.getStudentId()
                || note.getLecture().getId() != requestDTO.getLectureId() || note.getWeek() != requestDTO.getWeek()) {
            return new ToClientResponse<BoardResponse>("Error: Owner and/or lecture doesn't match.", 0, null);
        }

        boardRepository.delete(note);
        BoardResponse deletedNote = new BoardResponse(note.getTitle(), note.getContent());

        return new ToClientResponse<BoardResponse>("Note Removed.", 1, deletedNote);
    }

    protected Lecture getLecture(Long id) {
        return lectureRepository.findById(id).get();
    }

    protected Student getStudent(String uuid) {
        return studentRepository.findByUuid(uuid);
    }

}
