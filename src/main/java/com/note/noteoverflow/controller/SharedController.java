package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.LikeNoteDto;
import com.note.noteoverflow.dto.NoteIdDto;
import com.note.noteoverflow.dto.response.NotificationResponse;
import com.note.noteoverflow.dto.response.SharedResponse;
import com.note.noteoverflow.dto.response.SharedWithCommentsResponse;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.service.LikeNoteService;
import com.note.noteoverflow.service.PaginationService;
import com.note.noteoverflow.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/notes")
public class SharedController {

    private final SharedService sharedService;

    private final PaginationService paginationService;

    private final LikeNoteService likeNoteService;

    @GetMapping
    public String notes(@AuthenticationPrincipal NotePrincipal principal,
                        @RequestParam(required = false) String query,
                        @PageableDefault(size = 10, sort = "view_count", direction = Sort.Direction.DESC) Pageable pageable,
                        ModelMap map) {
        Long loginId = 0L;
        if (principal != null) {
            loginId = principal.id();
        }

        Page<SharedResponse> notes = sharedService.noteList(query, pageable).map(SharedResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), notes.getTotalPages());
        NotificationResponse notificationResponse = sharedService.getNotification(loginId);

        if (principal != null)
            map.addAttribute("principalId", principal.id());

        map.addAttribute("page", "notes");
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("notes", notes);
        map.addAttribute("notificationResponse", notificationResponse);
        return "notes/index";
    }

    // 노트 상세 페이지
    @GetMapping("/detail/{noteId}")
    public String note(@PathVariable Long noteId,
                       @RequestParam(defaultValue = "0") Long ntcation,
                       ModelMap map,
                       @AuthenticationPrincipal NotePrincipal principal) {
        Long loginId = 0L;
        if (principal != null) {
            loginId = principal.id();
        }

        if (ntcation != 0) {
            sharedService.notificationReading(ntcation);
        }
        SharedWithCommentsResponse sharedNote = sharedService.getSharedWithComments(noteId, loginId);
        NotificationResponse notificationResponse = sharedService.getNotification(loginId);
        List<NoteIdDto> relatedNotes = sharedService.getRelatedNote(noteId);

        map.addAttribute("page", "notes");
        map.addAttribute("sharedNote", sharedNote);
        map.addAttribute("sharedNoteComments", sharedNote.sharedNoteCommentResponses());
        map.addAttribute("notificationResponse", notificationResponse);
        map.addAttribute("relatedNotes", relatedNotes);
        return "notes/detail";
    }

    // 개인 노트 공유
    @PostMapping("/{noteId}/shared")
    public ResponseEntity<SharedResponse> noteShared(@PathVariable Long noteId,
                                                 @AuthenticationPrincipal NotePrincipal principal) {
        SharedResponse result = SharedResponse.from(sharedService.noteShared(principal.toDto(), noteId));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 개인 노트 공유 취소
    @PostMapping("/{noteId}/sharedCancel")
    public ResponseEntity<Integer> noteSharedCancel(@PathVariable Long noteId,
                                                    @AuthenticationPrincipal NotePrincipal principal) {
        int result = sharedService.noteSharedCancel(principal.toDto(), noteId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 추천 검색어
    @ResponseBody
    @GetMapping("/recommend")
    public ResponseEntity<List<String>> recommendedQuery(@RequestParam("query") String query) {
        List<String> tags = sharedService.recommendedQuery(query);
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    // 좋아요 추가
    @ResponseBody
    @PostMapping("/like-note/add/{noteId}")
    public ResponseEntity<Object> likeNoteAdd(@PathVariable Long noteId, @AuthenticationPrincipal NotePrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.OK).body(Integer.valueOf(-1));
        }
        LikeNoteDto likeNote = likeNoteService.likeNoteAdd(noteId, principal.toDto());
        return ResponseEntity.status(HttpStatus.OK).body(likeNote);
    }

    // 좋아요 삭제
    @ResponseBody
    @PostMapping("/like-note/delete/{noteId}")
    public ResponseEntity<Integer> likeNoteDelete(@PathVariable Long noteId, @AuthenticationPrincipal NotePrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.OK).body(-1);
        }
        Integer result = likeNoteService.likeNoteDelete(noteId, principal.toDto());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
