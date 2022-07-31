package com.proejct.ClassActionClaim.controller;

import com.proejct.ClassActionClaim.dto.RequestBody.NotesRequestDTO;
import com.proejct.ClassActionClaim.dto.ResponseBody.NotesResponseDTO;
import com.proejct.ClassActionClaim.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {

    private final NotesService notesService;

    @PostMapping
    public NotesResponseDTO saveNotes(@RequestBody NotesRequestDTO requestDTO) {
        return notesService.saveNote(requestDTO);
    }
}
