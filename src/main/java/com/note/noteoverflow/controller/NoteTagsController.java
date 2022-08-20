package com.note.noteoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/tags")
@Controller
public class NoteTagsController {

    @GetMapping
    public String tags(ModelMap map) {
        map.addAttribute("tags", List.of());
        return "tags/tags";
    }

}
