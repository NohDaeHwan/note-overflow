package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUserEmail(String username);

    @Query(value = "SELECT u FROM UserAccount u WHERE u.nickname LIKE %:nickname%")
    Page<UserAccount> findUsers(@Param("nickname") String nickname, Pageable pageable);
}
