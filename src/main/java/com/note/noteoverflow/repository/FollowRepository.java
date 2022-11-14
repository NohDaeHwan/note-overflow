package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByUserAccountIdAndFollowId(Long id, Long loginId);

    void deleteByUserAccountIdAndFollowId(Long userId, Long id);

    @Query(value = "SELECT f.followId FROM Follow f WHERE f.userAccount.id = :userId")
    List<Long> findByUserAccount(Long userId);

    List<Follow> findByUserAccountId(Long userId);

    @Query(value = "SELECT f.userAccount.id FROM Follow f WHERE f.followId = :userId")
    List<Long> findByFollowId(Long userId);
}
