package com.proejct.ClassActionClaim.controller;

import com.proejct.ClassActionClaim.domain.Notes;
import com.proejct.ClassActionClaim.dto.RequestBody.NotesRequestDTO;
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
    public ToClientResponse<List<NotesResponseDTO>> getNotes(@PathVariable Integer week, @ModelAttribute NotesRequestDTO requestDTO) {
        return notesService.getNotesByWeek(week, requestDTO);
    }

    @PostMapping("/save")
    public NotesResponseDTO saveNotes(@RequestBody NotesRequestDTO requestDTO) {
        return notesService.saveNote(requestDTO);
    }
}
