package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.request.NoteRequest;
import com.note.noteoverflow.dto.response.NoteResponse;
import com.note.noteoverflow.dto.response.NotificationResponse;
import com.note.noteoverflow.dto.response.UserAccountResponse;
import com.note.noteoverflow.dto.response.UserAccountsResponse;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.service.FollowService;
import com.note.noteoverflow.service.MypageService;
import com.note.noteoverflow.service.PaginationService;
import com.note.noteoverflow.service.SharedService;
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

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserAccountController {

    private final MypageService mypageService;

    private final PaginationService paginationService;

    private final SharedService sharedService;

    private final FollowService followService;

    @GetMapping("/users")
    public String users(@RequestParam(defaultValue = "") String user,
                        @PageableDefault(size = 20) Pageable pageable,
                        @AuthenticationPrincipal NotePrincipal principal,
                        ModelMap map
    ) {
        Long loginId = 0L;
        if (principal != null) {
            loginId = principal.id();
        }
        Page<UserAccountsResponse> users = mypageService.userSearch(user, pageable).map(UserAccountsResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), users.getTotalPages());
        NotificationResponse notificationResponse = sharedService.getNotification(loginId);

        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("users", users);
        map.addAttribute("notificationResponse", notificationResponse);
        return "users/users";
    }

    // User 마이페이지
    @GetMapping("/users/{userId}")
    public String user(@PathVariable Long userId,
                       @AuthenticationPrincipal NotePrincipal principal,
                       ModelMap map
    ) {
        Long loginId = 0L;
        if (principal != null) {
            loginId = principal.id();
        }
        if (principal != null && userId.equals(principal.id())) {
            mypageService.privateNoteList(userId, map);
        }

        mypageService.sharedNoteList(userId, map);
        int following = followService.followingList(userId);
        UserAccountResponse user = UserAccountResponse.from(mypageService.userDetail(userId), following);
        NotificationResponse notificationResponse = sharedService.getNotification(loginId);

        map.addAttribute("noteUser", user);
        map.addAttribute("notificationResponse", notificationResponse);
        if (principal != null)
            map.addAttribute("principalId", principal.id());
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

    // 팔요우 추가
    @ResponseBody
    @PostMapping("/user-follow/add/{userId}")
    public ResponseEntity<Integer> likeNoteAdd(@PathVariable Long userId,
                                              @AuthenticationPrincipal NotePrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.OK).body(Integer.valueOf(-1));
        }
        int followDto = mypageService.followAdd(userId, principal.toDto());
        return ResponseEntity.status(HttpStatus.OK).body(followDto);
    }

    // 팔로우 삭제
    @ResponseBody
    @PostMapping("/user-follow/delete/{userId}")
    public ResponseEntity<Integer> likeNoteDelete(@PathVariable Long userId,
                                                  @AuthenticationPrincipal NotePrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.OK).body(-1);
        }
        int result = mypageService.followDelete(userId, principal.toDto());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
