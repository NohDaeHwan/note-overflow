package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.FollowDto;

import java.time.LocalDateTime;

public record FollowResponse(
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
    public static FollowResponse of(Long id, Long userId, Long followId, String followUserEmail,
                                    String followNickname, LocalDateTime createdAt, String createdBy,
                                    LocalDateTime modifiedAt, String modifiedBy) {
        return new FollowResponse(id, userId, followId, followUserEmail, followNickname,
                createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static FollowResponse from(FollowDto dto) {
        return new FollowResponse(
                dto.id(),
                dto.userId(),
                dto.followId(),
                dto.followUserEmail(),
                dto.followNickname(),
                dto.createdAt(),
                dto.createdBy(),
                dto.modifiedAt(),
                dto.modifiedBy()
        );
    }

}
