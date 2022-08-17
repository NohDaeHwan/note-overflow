package com.note.noteoverflow.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteTagsController.class)
class NoteTagsControllerTest {

    private final MockMvc mvc;

    public NoteTagsControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 태그 리스트 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingSharedNotesView_thenReturnsSharedNotesView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("tags"));
    }

}
