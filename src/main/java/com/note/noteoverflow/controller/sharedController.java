package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.response.SharedResponse;
import com.note.noteoverflow.dto.response.SharedWithCommentsResponse;
import com.note.noteoverflow.dto.security.NotePrincipal;
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
public class sharedController {

    private final SharedService sharedService;

    private final PaginationService paginationService;

    @GetMapping
    public String notes(
            @RequestParam(required = false) String query,
            @PageableDefault(size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "popular") String tab,
            ModelMap model
    ) {
        Page<SharedResponse> notes = sharedService.noteList(query, tab, pageable).map(SharedResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), notes.getTotalPages());
        System.out.println(barNumbers);
        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("notes", notes);
        return "notes/index";
    }

    // 노트 상세 페이지
    @GetMapping("/detail/{noteId}")
    public String note(@PathVariable Long noteId, ModelMap map) {
        SharedWithCommentsResponse sharedNote = SharedWithCommentsResponse.from(sharedService.getSharedWithComments(noteId));

        map.addAttribute("sharedNote", sharedNote);
        map.addAttribute("sharedNoteComments", sharedNote.sharedNoteCommentResponses());

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
        System.out.println(tags);
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

}
