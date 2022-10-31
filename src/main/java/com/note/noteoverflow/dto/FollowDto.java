package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Follow;

import java.time.LocalDateTime;

public record FollowDto(
        Long id,
        Long userId,
        Long followId,
        String followUserEmail,
        String followNickname,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static FollowDto of(Long id, Long userId, Long followId, String followUserEmail,
                     String followNickname, LocalDateTime createdAt, String createdBy,
                     LocalDateTime modifiedAt, String modifiedBy) {
        return new FollowDto(id, userId, followId, followUserEmail, followNickname,
                createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static FollowDto from(Follow entity) {
        return new FollowDto(
                entity.getId(),
                entity.getUserAccount().getId(),
                entity.getFollowId(),
                entity.getFollowUserEmail(),
                entity.getFollowNickname(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

}
