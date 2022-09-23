package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.NoteTags;

import java.time.LocalDateTime;

public record NoteTagsDto(
        Long id,
        Long noteId,
        String tag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static NoteTagsDto of(Long id, Long noteId, String tag, LocalDateTime createdAt, String createdBy,
                                 LocalDateTime modifiedAt, String modifiedBy) {
        return new NoteTagsDto(id, noteId, tag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static NoteTagsDto of(String tag) {
        return new NoteTagsDto(null, null, tag, null, null, null, null);
    }

    public static NoteTagsDto from(NoteTags entity) {
        return new NoteTagsDto(
                entity.getId(),
                entity.getNote().getId(),
                entity.getTag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public NoteTags toEntity(Note note) {
        return NoteTags.of(note, tag);
    }

}
