package com.note.noteoverflow.service;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.NoteTags;
import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.dto.NoteCategoryDto;
import com.note.noteoverflow.dto.UserAccountDto;
import com.note.noteoverflow.dto.request.NoteRequest;
import com.note.noteoverflow.repository.NoteRepository;
import com.note.noteoverflow.repository.NoteTagsRepository;
import com.note.noteoverflow.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final UserAccountRepository userAccountRepository;

    private final NoteTagsRepository noteTagsRepository;

    // 개인 노트 작성
    @Transactional
    public int saveRequest(NoteRequest request, UserAccountDto userAccountDto) {
        try {
            UserAccount userAccount = userAccountRepository.findById(userAccountDto.id()).get();
            Note note = noteRepository.save(Note.of(request.title(), request.mCategory(), request.sCategory(), request.content(),
                    request.sharing(), userAccount));
            if (!note.equals(null)) {
                for (int i = 0; i < request.noteTagsRequests().size(); i++) {
                    if (request.noteTagsRequests().get(i).tag() != null && !request.noteTagsRequests().get(i).tag().isBlank()) {
                        noteTagsRepository.save(NoteTags.of(note, request.noteTagsRequests().get(i).tag()));
                    }
                }
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("NoteService:노트작성()"+e.getMessage());
        }
        return -1;
    }

    public List<NoteCategoryDto> getCateogryList(Long userId) {
        return noteRepository.findByUserAccountId(userId, true);
    }
}
