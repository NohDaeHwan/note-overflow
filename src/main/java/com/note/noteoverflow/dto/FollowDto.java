package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Follow;

import java.time.LocalDateTime;

public record FollowDto(
        Long id,
        Long userId,
        String userEmail,
        String userNickname,
        Long followId,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static FollowDto of(Long id, Long userId, String userEmail, String userNickname,Long followId, LocalDateTime createdAt, String createdBy,
                     LocalDateTime modifiedAt, String modifiedBy) {
        return new FollowDto(id, userId, userEmail, userNickname, followId, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static FollowDto from(Follow entity) {
        return new FollowDto(
                entity.getId(),
                entity.getUserAccount().getId(),
                entity.getUserAccount().getUserEmail(),
                entity.getUserAccount().getNickname(),
                entity.getFollowId(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

}
