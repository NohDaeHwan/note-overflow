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

    private final UserAccountRepository userAccountRepository;

    private final LikeNoteRepository likeNoteRepository;

    // 좋아요 생성
    public LikeNoteDto likeNoteAdd(Long noteId, UserAccountDto dto) {
        Shared shared = sharedRepository.findByNote_Id(noteId).get(); // 좋아요을 누른 노트
        shared.setLikeCount(shared.getLikeCount()+1);
        sharedRepository.save(shared);
        return LikeNoteDto.from(likeNoteRepository.save(LikeNote.of(shared, dto.id(), dto.userEmail(), dto.nickname()))); // 공유 노트에 좋아요 저장
    }

    public Integer likeNoteDelete(Long noteId, UserAccountDto toDto) {
        Shared shared = sharedRepository.findByNote_Id(noteId).get(); // 좋아요을 누른 노트
        int likeCount = shared.getLikeCount()-1;
        shared.setLikeCount(shared.getLikeCount()-1);
        sharedRepository.save(shared);
        Integer result = likeNoteRepository.deleteBySharedNoteIdAndLikeId(shared.getId(), toDto.id());
        if (result.intValue() == 1) {
            System.out.println("작동");
            return likeCount;
        } else {
            return -2;
        }
    }

}
