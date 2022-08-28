package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.response.SharedResponse;
import com.note.noteoverflow.dto.response.SharedWithCommentsResponse;
import com.note.noteoverflow.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notes")
public class sharedController {

    private final SharedService sharedService;

    @GetMapping
    public String notes(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<SharedResponse> notes = sharedService.searchSharedNote(searchValue, pageable).map(SharedResponse::from);

        map.addAttribute("notes", notes);
        return "notes/index";
    }

    @GetMapping("/{noteId}")
    public String note(@PathVariable Long noteId, ModelMap map) {
        SharedWithCommentsResponse sharedNote = SharedWithCommentsResponse.from(sharedService.getSharedWithComments(noteId));

        map.addAttribute("sharedNote", sharedNote);
        map.addAttribute("sharedNoteComments", sharedNote.sharedNoteCommentResponses());

        return "notes/detail";
    }

}
