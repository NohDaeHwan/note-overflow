package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.NoteCategoryDto;
import com.note.noteoverflow.dto.request.NoteRequest;
import com.note.noteoverflow.dto.response.NotificationResponse;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.service.NoteService;
import com.note.noteoverflow.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;

    private final SharedService sharedService;

    @GetMapping("/notes/create")
    public String noteCreate(@AuthenticationPrincipal NotePrincipal principal,
                             ModelMap map) {
        NotificationResponse notificationResponse = sharedService.getNotification(principal.id());
        List<NoteCategoryDto> categoryList = noteService.getCateogryList(principal.id());

        map.addAttribute("notificationResponse", notificationResponse);
        map.addAttribute("categoryList", categoryList);
        return "notes/create";
    }

    // 개인 노트 만들기
    @ResponseBody
    @PostMapping("/save_request")
    public ResponseEntity<Integer> saveRequest(@RequestBody NoteRequest noteRequest,
                                               @AuthenticationPrincipal NotePrincipal principal) {
        int result = noteService.saveRequest(noteRequest, principal.toDto());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
