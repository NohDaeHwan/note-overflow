package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.LikeNote;
import com.note.noteoverflow.domain.Shared;

import java.time.LocalDateTime;

public record LikeNoteDto(
        Long id,
        SharedDto sharedDto,
        Long likeId,
        String likeUserEmail,
        String likeNickname,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static LikeNoteDto of(Long id, SharedDto sharedDto, Long likeId, String likeUserEmail, String likeNickname,
                                 LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new LikeNoteDto(id, sharedDto, likeId, likeUserEmail, likeNickname, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static LikeNoteDto from(LikeNote likeNote) {
        return new LikeNoteDto(
                likeNote.getId(),
                SharedDto.from(likeNote.getSharedNote()),
                likeNote.getLikeId(),
                likeNote.getLikeUserEmail(),
                likeNote.getLikeNickname(),
                likeNote.getCreatedAt(),
                likeNote.getCreatedBy(),
                likeNote.getModifiedAt(),
                likeNote.getModifiedBy()
        );
    }

    public LikeNote toEntity(Shared sharedNote) {
        return LikeNote.of(sharedNote, likeId, likeUserEmail, likeNickname);
    }

}
