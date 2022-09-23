package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.request.NoteRequest;
import com.note.noteoverflow.dto.response.NoteResponse;
import com.note.noteoverflow.dto.response.UserAccountResponse;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserAccountController {

    private final MypageService mypageService;

    @GetMapping("/users")
    public String users(@RequestParam(defaultValue = "") String user,
                        @PageableDefault(size = 20) Pageable pageable,
                        ModelMap map) {
        Page<UserAccountResponse> users = mypageService.userSearch(user, pageable).map(UserAccountResponse::from);
        System.out.println(users.getContent().get(0));
        map.addAttribute("users", users.getContent());
        return "users/users";
    }

    // User 마이페이지
    @GetMapping("/users/{userId}")
    public String user(@PathVariable Long userId, @AuthenticationPrincipal NotePrincipal principal, ModelMap model) {
        if (principal != null && userId.equals(principal.id())) {
            mypageService.privateNoteList(userId, model);
        }
        mypageService.sharedNoteList(userId, model);
        UserAccountResponse user = UserAccountResponse.from(mypageService.userDetail(userId));
        model.addAttribute("noteUser", user);
        if (principal != null)
            model.addAttribute("principalId", principal.id());
        return "users/mypage";
    }

    // 마이페이지에서 노트 보기
    @GetMapping("/mypage-note/{noteId}")
    public ResponseEntity<NoteResponse> noteRequest(@PathVariable Long noteId) {
        NoteResponse note = NoteResponse.from(mypageService.userAccountNote(noteId));
        return ResponseEntity.status(HttpStatus.OK).body(note);
    }

    // 마이페이지에서 노트 수정
    @GetMapping("/note_update/{noteId}")
    public ResponseEntity<NoteResponse> noteRequest(@PathVariable Long noteId, @AuthenticationPrincipal NotePrincipal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.OK).body(null);
        NoteResponse note = NoteResponse.from(mypageService.userAccountNote(noteId));
        return ResponseEntity.status(HttpStatus.OK).body(note);
    }

    // 마이페이지에서 노트 수정 요청
    @PostMapping("/update_request")
    public ResponseEntity<NoteResponse> updateRequest(@RequestBody NoteRequest noteRequest, @AuthenticationPrincipal NotePrincipal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.OK).body(null);
        NoteResponse note = NoteResponse.from(mypageService.updateRequest(noteRequest.toDto(principal.toDto())));
        return ResponseEntity.status(HttpStatus.OK).body(note);
    }

}
