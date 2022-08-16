package com.note.noteoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/notes")
@Controller
public class NoteController {

    @GetMapping
    public String notes(ModelMap map) {
        map.addAttribute("notes", List.of());
        return "notes/index";
    }

}
