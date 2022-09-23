package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.request.NoteRequest;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/notes/create")
    public String noteCreate() {
        return "notes/create";
    }

    // 개인 노트 만들기
    @ResponseBody
    @PostMapping("/save_request")
    public ResponseEntity<Integer> saveRequest(@RequestBody NoteRequest noteRequest,
                                               @AuthenticationPrincipal NotePrincipal principal) {
        System.out.println(noteRequest);
        int result = noteService.saveRequest(noteRequest.toDto(principal.toDto()));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
