package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.response.SharedResponse;
import com.note.noteoverflow.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class GlobalController {

    private final SharedService sharedService;

    @GetMapping("/")
    public String index(ModelMap map) {
        List<SharedResponse> sharedNotes = sharedService.getIndexShared().stream()
                .map(SharedResponse::from).collect(Collectors.toUnmodifiableList());
        System.out.println(sharedNotes);
        map.addAttribute("sharedNotes", sharedNotes);
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
