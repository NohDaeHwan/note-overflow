package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM notification WHERE user_account_id = :loginId AND reading = :reading ORDER BY created_at DESC")
    List<Notification> findByUserAccountIdAndReading(Long loginId, boolean reading);

}
