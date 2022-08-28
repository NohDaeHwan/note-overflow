package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Shared;
import com.note.noteoverflow.domain.SharedNoteComment;
import com.note.noteoverflow.domain.UserAccount;

import java.time.LocalDateTime;

public record SharedNoteCommentDto(
        Long id,
        Long noteId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static SharedNoteCommentDto of(Long id, Long noteId, UserAccountDto userAccountDto, String content,
                                          LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new SharedNoteCommentDto(id, noteId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static SharedNoteCommentDto from(SharedNoteComment entity) {
        return new SharedNoteCommentDto(
                entity.getId(),
                entity.getShared().getNote().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public SharedNoteComment toEntity(Shared shared, UserAccount userAccount) {
        return SharedNoteComment.of(content, shared, userAccount);
    }

}
