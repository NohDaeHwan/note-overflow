package com.note.noteoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{noteId}")
    public String note(@PathVariable Long noteId, ModelMap map) {
        map.addAttribute("note", "note");
        map.addAttribute("sharedNoteComment", List.of());
        return "notes/detail";
    }

    @GetMapping("/create")
    public String noteCreate() {
        return "notes/create";
    }

}
