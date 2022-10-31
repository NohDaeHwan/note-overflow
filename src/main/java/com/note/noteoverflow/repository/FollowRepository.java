package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByUserAccountId(Long userId);

    Follow findByUserAccountIdAndFollowId(Long id, Long loginId);

    void deleteByUserAccountIdAndFollowId(Long userId, Long id);

    List<Follow> findByFollowId(Long userId);
}
