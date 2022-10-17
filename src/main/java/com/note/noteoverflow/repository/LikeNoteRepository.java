package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.LikeNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeNoteRepository extends JpaRepository<LikeNote, Long> {
    LikeNote findBySharedNoteIdAndLikeId(Long id, Long userId);

    Integer deleteBySharedNoteIdAndLikeId(Long id, Long id1);
}
