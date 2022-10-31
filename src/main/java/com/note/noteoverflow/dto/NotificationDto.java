package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Notification;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record NotificationDto(
        Long id,
        Long userAccountId,
        Long noteId,
        boolean reading,
        Long userId,
        String nickname,
        String content,
        String timeResult,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static NotificationDto of(Long id, Long userAccountId, Long noteId, boolean reading, Long userId,
                           String nickname, String content, String timeResult, LocalDateTime createdAt, String createdBy,
                           LocalDateTime modifiedAt, String modifiedBy, LocalDateTime date) {
        return new NotificationDto(id, userAccountId, noteId, reading, userId, nickname,
                content, timeResult, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static NotificationDto from(Notification entity) {
        String result = null;
        LocalDateTime now = LocalDateTime.now();
        long year = ChronoUnit.YEARS.between(entity.getCreatedAt(), now);
        long month = ChronoUnit.MONTHS.between(entity.getCreatedAt(), now);
        long week = ChronoUnit.WEEKS.between(entity.getCreatedAt(), now);
        long day = ChronoUnit.DAYS.between(entity.getCreatedAt(), now);
        long hour = ChronoUnit.HOURS.between(entity.getCreatedAt(), now);
        long minute = ChronoUnit.MINUTES.between(entity.getCreatedAt(), now);
        long second = ChronoUnit.SECONDS.between(entity.getCreatedAt(), now);

        if (year >= 1) {
            result = year + "년전";
        } else if (month >= 1) {
            result = month + "달전";
        } else if (week >= 1) {
            result = week + "주전";
        } else if (day >= 1) {
            result = day + "일전";
        } else if (hour >= 1) {
            result = day + "시간전";
        } else if (hour >= 1) {
            result = minute + "분전";
        } else {
            result = minute + "초전";
        }

        System.out.println(result);
        return new NotificationDto(
                entity.getId(),
                entity.getUserAccount().getId(),
                entity.getNoteId(),
                entity.isReading(),
                entity.getUserId(),
                entity.getNickname(),
                entity.getContent(),
                result,
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

}
