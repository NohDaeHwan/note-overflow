package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Shared;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record SharedWithCommentsDto(
        Long id,
        NoteDto noteDto,
        UserAccountDto userAccountDto,
        Set<SharedNoteCommentDto> sharedNoteCommentDtos,
        int viewCount,
        int likeCount,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static SharedWithCommentsDto of(Long id, NoteDto noteDto, UserAccountDto userAccountDto, Set<SharedNoteCommentDto> sharedNoteCommentDtos,
                                 int viewCount, int likeCount, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new SharedWithCommentsDto(id, noteDto, userAccountDto, sharedNoteCommentDtos, viewCount, likeCount, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static SharedWithCommentsDto from(Shared entity) {
        return new SharedWithCommentsDto(
                entity.getId(),
                NoteDto.from(entity.getNote()),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getSharedNoteComments().stream()
                        .map(SharedNoteCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getViewCount(),
                entity.getLikeCount(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

}
