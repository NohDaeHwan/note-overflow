package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Notification;

import java.time.LocalDateTime;

public record NotificationDto(
        Long id,
        Long userAccountId,
        Long noteId,
        boolean reading,
        Long userId,
        String nickname,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static NotificationDto of(Long id, Long userAccountId, Long noteId, boolean reading, Long userId,
                           String nickname, String content, LocalDateTime createdAt, String createdBy,
                           LocalDateTime modifiedAt, String modifiedBy) {
        return new NotificationDto(id, userAccountId, noteId, reading, userId, nickname,
                content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static NotificationDto from(Notification entity) {
        return new NotificationDto(
                entity.getId(),
                entity.getUserAccount().getId(),
                entity.getNoteId(),
                entity.isReading(),
                entity.getUserId(),
                entity.getNickname(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

}
