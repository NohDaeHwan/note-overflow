package com.note.noteoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/users")
@Controller
public class UserAccountController {

    @GetMapping
    public String users(ModelMap map) {
        map.addAttribute("users", List.of());
        return "users/users";
    }

    @GetMapping("/{userAccountId}")
    public String user(@PathVariable Long userAccountId, ModelMap map) {
        map.addAttribute("userNotes", List.of());
        return "users/mypage";
    }

}
