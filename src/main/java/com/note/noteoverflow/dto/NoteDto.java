package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.NoteTags;
import com.note.noteoverflow.domain.UserAccount;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record NoteDto(
        Long id,
        UserAccountDto userAccountDto,
        List<NoteTagsDto> noteTagsDtos,
        String title,
        String mCategory,
        String sCategory,
        String content,
        boolean sharing,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static NoteDto of(Long id, UserAccountDto userAccountDto, List<NoteTagsDto> noteTagsDtos, String title, String mCategory,
                             String sCategory, String content, boolean sharing)
    {
        return new NoteDto(id, userAccountDto, noteTagsDtos, title, mCategory, sCategory, content, sharing, null, null, null, null);
    }

    public static NoteDto from(Note entity) {
        return new NoteDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getNoteTags().stream()
                        .map(NoteTagsDto::from)
                        .collect(Collectors.toUnmodifiableList()),
                entity.getTitle(),
                entity.getMCategory(),
                entity.getSCategory(),
                entity.getContent(),
                entity.isSharing(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Note toEntity() {
        return Note.of(title, mCategory, sCategory, content, sharing, userAccountDto.toEntity());
    }

}
