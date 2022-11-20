package com.note.noteoverflow.service;

import com.note.noteoverflow.domain.*;
import com.note.noteoverflow.dto.*;
import com.note.noteoverflow.dto.response.NotificationResponse;
import com.note.noteoverflow.dto.response.SharedResponse;
import com.note.noteoverflow.dto.response.SharedWithCommentsResponse;
import com.note.noteoverflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class SharedService {

    private final SharedRepository sharedRepository;

    private final NoteRepository noteRepository;

    private final NoteTagsRepository noteTagsRepository;

    private final UserAccountRepository userAccountRepository;

    private final LikeNoteRepository likeNoteRepository;

    private final NotificationRepository notificationRepository;

    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    public NotificationResponse getNotification(Long loginId) {
        List<NotificationDto> notificationDtos = notificationRepository.findByUserAccountIdAndReading(loginId, false)
                .stream().map(NotificationDto::from).collect(Collectors.toCollection(ArrayList::new));
        return NotificationResponse.of(notificationDtos, notificationDtos.size());
    }

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

    @Transactional
    public SharedWithCommentsResponse getSharedWithComments(Long noteId, Long loginId) {
        Shared shared = sharedRepository.findByNote_Id(noteId).get();
        shared.setViewCount(shared.getViewCount()+1);
        sharedRepository.save(shared);

        LikeNote likeNote = null;
        Follow follow = null;
        if (!loginId.equals(0L)) {
            likeNote = likeNoteRepository.findBySharedNoteIdAndLikeId(shared.getId(), loginId);
            follow = followRepository.findByUserAccountIdAndFollowId(shared.getUserAccount().getId(), loginId);
        }

        Boolean likeCheck;
        Boolean followCheck;
        if (likeNote == null) {
            likeCheck = false;
        } else {
            likeCheck = true;
        }
        if (follow == null) {
            followCheck = false;
        } else {
            followCheck = true;
        }
        return SharedWithCommentsResponse.of(SharedWithCommentsDto.from(shared), likeCheck, followCheck);
    }

    // 유저 팔로우 여부(마이페이지)
    @Transactional(readOnly = true)
    public boolean getFollow(Long userId, Long loginId) {
        Follow follow = followRepository.findByUserAccountIdAndFollowId(userId, loginId);
        Boolean followCheck;
        if (follow == null) {
            followCheck = false;
        } else {
            followCheck = true;
        }
        return followCheck;
    }

    @Transactional(readOnly = true)
    public SharedDto getShared(Long noteId) {
        return sharedRepository.findByNote_Id(noteId).map(SharedDto::from)
                .orElseThrow(() -> new EntityNotFoundException("노트가 없습니다 - noteId: " + noteId));
    }

    // 공유 노트 검색
    public Page<SharedDto> noteList(String query, Pageable page) {
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
        List<Follow> followList = followRepository.findByUserAccountId(dto.id());

        if (followList != null) {
            for (Follow follow : followList) {
                UserAccount user = userAccountRepository.findById(follow.getFollowId()).get();
                notificationRepository.save(Notification.of(user, noteId, false, userAccount.getId(),
                        userAccount.getUserImage(), userAccount.getNickname(), "님이 게시물을 올렸습니다."));
            }
        }

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

    public List<SharedDto> getIndexShared() {
        return sharedRepository.fidByCreatedAt().stream()
                .map(SharedDto::from).collect(Collectors.toUnmodifiableList());
    }

    public List<NoteIdDto> getRelatedNote(Long noteId) {
        Shared shared = sharedRepository.findByNote_Id(noteId).get();
        List<String> tags = new ArrayList<>();
        for (NoteTags noteTag : shared.getNote().getNoteTags()) {
            tags.add(noteTag.getTag());
        }

        List<NoteIdDto> dtos = noteTagsRepository.findByTag(tags, noteId);
        return dtos;
    }

    public void notificationReading(Long ntcation) {
        Notification notification = notificationRepository.findById(ntcation).get();
        notification.setReading(true);
        notificationRepository.save(notification);
    }
}
