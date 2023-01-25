package com.proejct.ClassActionClaim.controller;

import com.proejct.ClassActionClaim.dto.RequestBody.Board.BoardWriteRequest;
import com.proejct.ClassActionClaim.dto.RequestBody.BoardRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.BoardResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{week}")
    public ToClientResponse<List<BoardResponse>> getNotes(@PathVariable Long week, @ModelAttribute BoardRequest request) {
        return boardService.getNotesByWeek(week, request);
    }

    @PostMapping("/write")
    public BoardResponse writeBoard(@RequestBody BoardWriteRequest request) {

        return boardService.writeBoard(request);
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
