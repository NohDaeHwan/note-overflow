package com.note.noteoverflow.service;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.dto.NoteDto;
import com.note.noteoverflow.repository.NoteRepository;
import com.note.noteoverflow.repository.NoteTagsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final NoteTagsRepository noteTagsRepository;

    // 개인 노트 작성
    @Transactional
    public int saveRequest(NoteDto noteDto) {
        try {
            Note note = noteRepository.save(noteDto.toEntity());
            if (!note.equals(null)) {
                for (int i = 0; i < noteDto.noteTagsDtos().size(); i++) {
                    if (noteDto.noteTagsDtos().get(i).tag() != null && !noteDto.noteTagsDtos().get(i).tag().isBlank()) {
                        noteTagsRepository.save(noteDto.noteTagsDtos().get(i).toEntity(note));
                    }
                }
                log.trace("개인 노트 작성");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("NoteService:노트작성()"+e.getMessage());
        }
        return -1;
    }

}
