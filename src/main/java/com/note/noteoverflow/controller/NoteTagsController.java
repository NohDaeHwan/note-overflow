package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.TagListDto;
import com.note.noteoverflow.service.NoteTagsService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/tags")
@Controller
public class NoteTagsController {

    private final NoteTagsService noteTagsService;

    @GetMapping
    public String tags(@RequestParam(defaultValue = "") String tag,
                       @PageableDefault(size = 20) Pageable pageable,
                       ModelMap map
    ) {
        Page<TagListDto> tagList = noteTagsService.selectTagList(tag, pageable);
        map.addAttribute("tags", tagList.getContent());
        return "tags/tags";
    }

}
