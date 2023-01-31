package com.proejct.ClassActionClaim.controller;

import com.proejct.ClassActionClaim.dto.RequestBody.Board.BoardWriteRequest;
import com.proejct.ClassActionClaim.dto.RequestBody.BoardRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.BoardResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"notes", "board"})
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{week}")
    public ToClientResponse<List<BoardResponse>> getNotes(@PathVariable Long week, @ModelAttribute BoardRequest request) {
        return boardService.getNotesByWeek(week, request);
    }

    @PostMapping("/write")
    public ToClientResponse<BoardResponse> writeBoard(@RequestBody BoardWriteRequest request) {
        BoardResponse boardResponse = boardService.writeBoard(request);

        return new ToClientResponse<>("Board Saved.", 1, HttpStatus.OK, boardResponse);
    }

    @PostMapping("/update")
    public ToClientResponse<BoardResponse> updateNotes(@ModelAttribute Long noteId, @RequestBody BoardRequest request) {
        return boardService.updateNote(noteId, request);
    }

    @PostMapping("/remove")
    public ToClientResponse<BoardResponse> removeNotes(@ModelAttribute Long noteId, @RequestBody BoardRequest request) {
        return boardService.removeNote(noteId, request);
    }
}
