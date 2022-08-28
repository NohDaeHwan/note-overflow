package com.note.noteoverflow.service;

import com.note.noteoverflow.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;

}
