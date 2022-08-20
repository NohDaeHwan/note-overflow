package com.note.noteoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GlobalController {

    @GetMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("sharedNotes", List.of());
        return "index";
    }

    // 로그인페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 소개페이지
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // 회원가입페이지
    @GetMapping("/join")
    public String join() {
        return "join";
    }

}
