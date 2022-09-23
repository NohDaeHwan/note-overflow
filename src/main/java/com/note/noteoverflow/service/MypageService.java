package com.note.noteoverflow.service;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.dto.NoteDto;
import com.note.noteoverflow.dto.UserAccountDto;
import com.note.noteoverflow.repository.NoteRepository;
import com.note.noteoverflow.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final NoteRepository noteRepository;

    private final UserAccountRepository userAccountRepository;

    // 마이페이지에 개인 노트 리스트 불러오기
    @Transactional
    public void privateNoteList(Long userId, ModelMap model) {
        List<Note> privateList;
        List<Object[]> privateCategory;
        List<String> pCategory = new ArrayList<>();

        privateList = noteRepository.findPrivateNote(userId,false);
        privateCategory = noteRepository.findGroupBymCategory(userId, false);

        for (Object[] objects : privateCategory) {
            pCategory.add((String) objects[0]);
        }

        model.addAttribute("privateList", privateList);
        model.addAttribute("privateCategory", pCategory);
    }

    // 마이페이지에 공유 노트 리스트 불러오기
    @Transactional
    public void sharedNoteList(Long userId, ModelMap model) {
        List<Note> sharedList;
        List<Object[]> sharedCategory;
        List<String> sCategory = new ArrayList<>();

        sharedList = noteRepository.findPrivateNote(userId, true);
        sharedCategory = noteRepository.findGroupBymCategory(userId, true);

        for (Object[] objects : sharedCategory) {
            sCategory.add((String) objects[0]);
        }

        model.addAttribute("sharedList", sharedList);
        model.addAttribute("sharedCategory", sCategory);

    }

    @Transactional
    public UserAccountDto userDetail(Long userId) {
        return UserAccountDto.from(userAccountRepository.findById(userId).get());
    }

    // 마이페이지 노트 세부사항
    @Transactional
    public NoteDto userAccountNote(Long noteId) {
        return NoteDto.from(noteRepository.findById(noteId).get());
    }


    @Transactional
    public NoteDto updateRequest(NoteDto dto) {
        Note dbNote = noteRepository.getReferenceById(dto.id());
        dbNote.setTitle(dto.title());
        dbNote.setMCategory(dto.mCategory());
        dbNote.setSCategory(dto.sCategory());
        dbNote.setContent(dto.content());
        NoteDto noteResult = NoteDto.from(noteRepository.save(dbNote));
        return noteResult;
    }

    @Transactional
    public Page<UserAccountDto> userSearch(String nickname, Pageable pageable) {
        return userAccountRepository.findUsers(nickname, pageable).map(UserAccountDto::from);

    }

}
