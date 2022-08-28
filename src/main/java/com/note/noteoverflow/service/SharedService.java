package com.note.noteoverflow.service;

import com.note.noteoverflow.dto.SharedDto;
import com.note.noteoverflow.dto.SharedWithCommentsDto;
import com.note.noteoverflow.repository.NoteRepository;
import com.note.noteoverflow.repository.NoteTagsRepository;
import com.note.noteoverflow.repository.SharedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class SharedService {

    private final SharedRepository sharedRepository;

    private final NoteRepository noteRepository;

    private final NoteTagsRepository noteTagsRepository;

    @Transactional(readOnly = true)
    public Page<SharedDto> searchSharedNote(String searchKeyword, Pageable pageable) {
        List<Long> sharedNoteListIds = new ArrayList<>();

        if (searchKeyword == null || searchKeyword.isBlank()) {
            return sharedRepository.findAll(pageable).map(SharedDto::from);
        }

        List<Long> noteIds = new ArrayList<>();
        noteIds = noteTagsRepository.findAllNoteIds(searchKeyword);
        sharedNoteListIds = noteRepository.findAllIds(noteIds);
        return sharedRepository.findByNote_IdIn(sharedNoteListIds, pageable).map(SharedDto::from);
    }

    @Transactional(readOnly = true)
    public SharedWithCommentsDto getSharedWithComments(Long noteId) {
        return sharedRepository.findByNote_Id(noteId).map(SharedWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("노트가 없습니다 - noteId: " + noteId));
    }

    @Transactional(readOnly = true)
    public SharedDto getShared(Long noteId) {
        return sharedRepository.findByNote_Id(noteId).map(SharedDto::from)
                .orElseThrow(() -> new EntityNotFoundException("노트가 없습니다 - noteId: " + noteId));
    }

}
