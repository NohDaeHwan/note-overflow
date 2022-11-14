package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.FollowDto;

import java.time.LocalDateTime;

public record FollowResponse(
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
    public static FollowResponse of(Long id, Long userId, String userEmail, String userNickname,
                                    Long followId, LocalDateTime createdAt, String createdBy,
                                    LocalDateTime modifiedAt, String modifiedBy) {
        return new FollowResponse(id, userId, userEmail, userNickname, followId, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static FollowResponse from(FollowDto dto) {
        return new FollowResponse(
                dto.id(),
                dto.userId(),
                dto.userEmail(),
                dto.userNickname(),
                dto.followId(),
                dto.createdAt(),
                dto.createdBy(),
                dto.modifiedAt(),
                dto.modifiedBy()
        );
    }

}
