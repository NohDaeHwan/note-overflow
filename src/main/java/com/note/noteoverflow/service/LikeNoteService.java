package com.note.noteoverflow.service;

import com.note.noteoverflow.domain.*;
import com.note.noteoverflow.dto.LikeNoteDto;
import com.note.noteoverflow.dto.UserAccountDto;
import com.note.noteoverflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeNoteService {

    private final SharedRepository sharedRepository;

    private final LikeNoteRepository likeNoteRepository;

    private final NotificationRepository notificationRepository;

    // 좋아요 생성
    public LikeNoteDto likeNoteAdd(Long noteId, UserAccountDto dto) {
        Shared shared = sharedRepository.findByNote_Id(noteId).get(); // 좋아요을 누른 노트
        shared.setLikeCount(shared.getLikeCount()+1);
        sharedRepository.save(shared);
        notificationRepository.save(Notification.of(shared.getUserAccount(), noteId, false, dto.id(),
                dto.userImage(), dto.nickname(), "님이 회원님의 게시물에 좋아요를 눌렀습니다."));
        return LikeNoteDto.from(likeNoteRepository.save(LikeNote.of(shared, dto.id(), dto.userEmail(), dto.nickname()))); // 공유 노트에 좋아요 저장
    }

    public Integer likeNoteDelete(Long noteId, UserAccountDto dto) {
        Shared shared = sharedRepository.findByNote_Id(noteId).get(); // 좋아요을 누른 노트
        shared.setLikeCount(shared.getLikeCount()-1);
        sharedRepository.save(shared);
        notificationRepository.save(Notification.of(shared.getUserAccount(), noteId, false, dto.id(),
                dto.userImage(), dto.nickname(), "님이 회원님의 게시물에 좋아요를 취소했습니다."));
        Integer result = likeNoteRepository.deleteBySharedNoteIdAndLikeId(shared.getId(), dto.id());
        return shared.getLikeCount();
    }

}
