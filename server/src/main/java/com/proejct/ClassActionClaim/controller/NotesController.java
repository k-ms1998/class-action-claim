package com.proejct.ClassActionClaim.controller;

import com.proejct.ClassActionClaim.dto.RequestBody.NotesRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.NotesResponseDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {

    private final NotesService notesService;

    @GetMapping("/{week}")
    public ToClientResponse<List<NotesResponseDTO>> getNotes(@PathVariable Long week, @ModelAttribute NotesRequest request) {
        return notesService.getNotesByWeek(week, request);
    }

    @PostMapping("/save")
    public NotesResponseDTO saveNotes(@RequestBody NotesRequest request) {
        return notesService.saveNote(request);
    }

    @PostMapping("/update")
    public ToClientResponse<NotesResponseDTO> updateNotes(@ModelAttribute Long noteId, @RequestBody NotesRequest request) {
        return notesService.updateNote(noteId, request);
    }

    @PostMapping("/remove")
    public ToClientResponse<NotesResponseDTO> removeNotes(@ModelAttribute Long noteId, @RequestBody NotesRequest request) {
        return notesService.removeNote(noteId, request);
    }
}
