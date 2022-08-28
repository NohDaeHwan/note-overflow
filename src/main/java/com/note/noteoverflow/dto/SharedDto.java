package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.Shared;
import com.note.noteoverflow.domain.UserAccount;

import java.time.LocalDateTime;

public record SharedDto(
        Long id,
        NoteDto noteDto,
        UserAccountDto userAccountDto,
        int viewCount,
        int likeCount,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public  static SharedDto of(Long id, NoteDto noteDto, UserAccountDto userAccountDto, int viewCount, int likeCount,
                                LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy)
    {
        return new SharedDto(id, noteDto, userAccountDto, viewCount, likeCount, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static SharedDto from(Shared entity) {
        return new SharedDto(
                entity.getId(),
                NoteDto.from(entity.getNote()),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getViewCount(),
                entity.getLikeCount(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Shared toEntity(Note note, UserAccount userAccount) {
        return Shared.of(userAccount, note, viewCount, likeCount);
    }

}
