package com.note.noteoverflow.service;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.Shared;
import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.dto.SharedDto;
import com.note.noteoverflow.dto.SharedWithCommentsDto;
import com.note.noteoverflow.dto.UserAccountDto;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.repository.NoteRepository;
import com.note.noteoverflow.repository.NoteTagsRepository;
import com.note.noteoverflow.repository.SharedRepository;
import com.note.noteoverflow.repository.UserAccountRepository;
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

    private final UserAccountRepository userAccountRepository;

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

    // 공유 노트 검색
    public Page<SharedDto> noteList(String query, String tab, Pageable page) {
        List<Long> noteIds = new ArrayList<>();

        if (query == null) {
            noteIds = noteRepository.findBySharing();
        } else {
            noteIds = noteTagsRepository.findGroupByTag(query);
        }
        return sharedRepository.findByQueryResult(noteIds, page).map(SharedDto::from);
    }

    // 노트 공유
    @Transactional
    public SharedDto noteShared(UserAccountDto dto, Long noteId) {
        Note note = noteRepository.getReferenceById(noteId);
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.id());

        note.setSharing(true);
        userAccount.setSharedCount(userAccount.getSharedCount()+1);

        Shared sharedNote = Shared.of(userAccount, note, 0, 0);

        userAccountRepository.save(userAccount);
        noteRepository.save(note);
        return SharedDto.from(sharedRepository.save(sharedNote));
    }

    // 노트 공유 취소
    @Transactional
    public int noteSharedCancel(UserAccountDto dto, Long noteId) {
        Note note = noteRepository.getReferenceById(noteId);
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.id());

        note.setSharing(false);
        userAccount.setSharedCount(userAccount.getSharedCount()-1);

        userAccountRepository.save(userAccount);
        noteRepository.save(note);
        return sharedRepository.deleteByNote_Id(note.getId());
    }

    // 추천 검색어
    public List<String> recommendedQuery(String query) {
        List<Long> noteIds = noteRepository.findBySharing();

        return noteTagsRepository.findAllTags(noteIds, query);
    }

}
