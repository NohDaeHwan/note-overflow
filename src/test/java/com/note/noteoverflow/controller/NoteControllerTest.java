package com.note.noteoverflow.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    private final MockMvc mvc;

    public NoteControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

   /* @DisplayName("[view][GET] 공유 노트 리스트 (노트 공유 게시판) 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingSharedNotesView_thenReturnsSharedNotesView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("notes/index"))
                .andExpect(model().attributeExists("notes"));
    }

    @DisplayName("[view][GET] 노트 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingNoteView_thenReturnsNoteView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("notes/detail"))
                .andExpect(model().attributeExists("note"))
                .andExpect(model().attributeExists("sharedNoteComment"));
    }

    @DisplayName("[view][GET] 노트 생성 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingNoteCreateView_thenReturnsNoteCreateView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/notes/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("notes/create"));
    }*/

}
