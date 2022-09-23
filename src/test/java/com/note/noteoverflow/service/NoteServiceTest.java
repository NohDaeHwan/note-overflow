package com.note.noteoverflow.service;

import com.note.noteoverflow.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@DisplayName("비즈니스 로직 - 노트")
@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @InjectMocks
    private NoteService sut;

    @Mock
    private NoteRepository noteRepository;

    @Test
    void given_when_then() {

    }

}
