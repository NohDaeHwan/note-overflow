package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.TagListDto;
import com.note.noteoverflow.dto.response.NotificationResponse;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.service.NoteTagsService;
import com.note.noteoverflow.service.PaginationService;
import com.note.noteoverflow.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/tags")
@Controller
public class NoteTagsController {

    private final NoteTagsService noteTagsService;

    private final PaginationService paginationService;

    private final SharedService sharedService;

    @GetMapping
    public String tags(@RequestParam(defaultValue = "") String tag,
                       @PageableDefault(size = 18) Pageable pageable,
                       @AuthenticationPrincipal NotePrincipal principal,
                       ModelMap map
    ) {
        Long loginId = 0L;
        if (principal != null) {
            loginId = principal.id();
        }

        Page<TagListDto> tagList = noteTagsService.selectTagList(tag, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), tagList.getTotalPages());
        NotificationResponse notificationResponse = sharedService.getNotification(loginId);
        System.out.println(tagList.getContent());

        map.addAttribute("page", "tags");
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("tags", tagList);
        map.addAttribute("notificationResponse", notificationResponse);
        return "tags/tags";
    }

}
