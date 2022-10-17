package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAccountIdAndReading(Long loginId, boolean reading);
}
