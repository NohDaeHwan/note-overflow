package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.LikeNoteDto;

import java.time.LocalDateTime;

public record LikeNoteResponse(
        Long likeId,
        String likeUserEmail,
        String likeNickname,
        LocalDateTime createdAt,
        String createdBy
) {
    public static LikeNoteResponse of(Long likeId, String likeUserEmail, String likeNickname,
                                      LocalDateTime createdAt, String createdBy) {
        return new LikeNoteResponse(likeId, likeUserEmail, likeNickname, createdAt, createdBy);
    }

    public static LikeNoteResponse from(LikeNoteDto dto) {
        return new LikeNoteResponse(
                dto.likeId(),
                dto.likeUserEmail(),
                dto.likeNickname(),
                dto.createdAt(),
                dto.createdBy()
        );
    }

}
