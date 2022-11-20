package com.note.noteoverflow.controller;

import com.note.noteoverflow.dto.request.JoinRequest;
import com.note.noteoverflow.dto.response.SharedResponse;
import com.note.noteoverflow.service.MypageService;
import com.note.noteoverflow.service.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class GlobalController {

    private final SharedService sharedService;

    private final MypageService mypageService;

    @GetMapping("/")
    public String index(ModelMap map) {
        List<SharedResponse> sharedNotes = sharedService.getIndexShared().stream()
                .map(SharedResponse::from).collect(Collectors.toUnmodifiableList());
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

    // 회원가입 요청
    @PostMapping("/join_request")
    public ResponseEntity<Integer> joinRequest(@RequestBody JoinRequest request) {
        int result = mypageService.joinRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
