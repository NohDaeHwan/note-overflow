package com.note.noteoverflow.service;

import com.note.noteoverflow.component.FileHandler;
import com.note.noteoverflow.domain.Follow;
import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.NoteTags;
import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.domain.type.RoleType;
import com.note.noteoverflow.dto.FollowingDto;
import com.note.noteoverflow.dto.NoteDto;
import com.note.noteoverflow.dto.UserAccountDto;
import com.note.noteoverflow.dto.request.JoinRequest;
import com.note.noteoverflow.dto.request.UserEditRequest;
import com.note.noteoverflow.dto.security.NotePrincipal;
import com.note.noteoverflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    
    private final FollowRepository followRepository;

    private final NoteTagsRepository noteTagsRepository;

    private final FileHandler fileHandler;

    private final PasswordEncoder passwordEncoder;

    // 마이페이지에 개인 노트 리스트 불러오기
    @Transactional
    public void privateNoteList(Long userId, ModelMap map) {
        List<Note> privateList;
        List<Object[]> privateCategory;
        List<String> pCategory = new ArrayList<>();

        privateList = noteRepository.findPrivateNote(userId,false);
        privateCategory = noteRepository.findGroupBymCategory(userId, false);

        for (Object[] objects : privateCategory) {
            pCategory.add((String) objects[0]);
        }

        map.addAttribute("privateList", privateList);
        map.addAttribute("privateCategory", pCategory);
    }

    // 마이페이지에 공유 노트 리스트 불러오기
    @Transactional
    public void sharedNoteList(Long userId, ModelMap map) {
        List<Note> sharedList;
        List<Object[]> sharedCategory;
        List<String> sCategory = new ArrayList<>();

        sharedList = noteRepository.findPrivateNote(userId, true);
        sharedCategory = noteRepository.findGroupBymCategory(userId, true);

        for (Object[] objects : sharedCategory) {
            sCategory.add((String) objects[0]);
        }

        map.addAttribute("sharedList", sharedList);
        map.addAttribute("sharedCategory", sCategory);

    }

    @Transactional
    public UserAccountDto userDetail(Long userId) {
        List<Long> followId = followRepository.findByUserAccount(userId);
        List<FollowingDto> follow = userAccountRepository.findByFollowId(followId);
        return UserAccountDto.from(userAccountRepository.findById(userId).get(), follow);
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
        Note noteResult = noteRepository.save(dbNote);
        List<NoteTags> noteTags = noteTagsRepository.findByNoteId(dto.id());
        for (int i = 0; i < 5; i++) {
            if (noteTags.size() >= (i+1)) {
                noteTags.get(i).setTag(dto.noteTagsDtos().get(i).tag());
                noteTagsRepository.save(noteTags.get(i));
            } else {
                if (!dto.noteTagsDtos().get(i).tag().equals(null) && !dto.noteTagsDtos().get(i).tag().equals(""))
                    noteTagsRepository.save(NoteTags.of(noteResult, dto.noteTagsDtos().get(i).tag()));
            }
        }
        return NoteDto.from(noteResult);
    }

    @Transactional
    public Page<UserAccountDto> userSearch(String nickname, Pageable pageable) {
        return userAccountRepository.findUsers(nickname, pageable).map(UserAccountDto::from);

    }

    @Transactional
    public int followAdd(Long userId, UserAccountDto toDto) {
        UserAccount userAccount = userAccountRepository.findById(userId).get();
        followRepository.save(Follow.of(userAccount, toDto.id()));
        return followRepository.findByUserAccountId(userId).size();
    }

    @Transactional
    public Integer followDelete(Long userId, UserAccountDto toDto) {
        followRepository.deleteByUserAccountIdAndFollowId(userId, toDto.id());
        return followRepository.findByUserAccountId(userId).size();
    }

    @Transactional
    public void userEdit(UserEditRequest request) throws Exception {
        String filePath = fileHandler.parseFileInfo(request.image());
        UserAccount userAccount = userAccountRepository.findByUserEmail(request.userEmail()).get();
        userAccount.setUserPhone(request.userPhone());
        userAccount.setNickname(request.nickname());
        userAccount.setUserImage(filePath);
        UserAccount result = userAccountRepository.save(userAccount);
        NotePrincipal.from(result);
    }

    public int joinRequest(JoinRequest request) {
        try {
            System.out.println(passwordEncoder.encode(request.userPassword()));
            userAccountRepository.save(UserAccount.of(request.userEmail(),
                    passwordEncoder.encode(request.userPassword()),
                    request.userName(), request.userPhone(), RoleType.USER, 0, "/assets/img/undraw_profile.jpg"));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 마이페이지 팔로워 모달
    @Transactional
    public int followModalDelete(Long userId, UserAccountDto toDto) {
        followRepository.deleteByUserAccountIdAndFollowId(toDto.id(), userId);
        return followRepository.findByUserAccountId(toDto.id()).size();
    }
}
