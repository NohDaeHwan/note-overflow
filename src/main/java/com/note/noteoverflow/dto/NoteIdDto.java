package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.Note;

import java.time.LocalDateTime;

public record NoteIdDto(
        Long id,
        String title,
        LocalDateTime createdAt,
        String createdBy
) {
    public static NoteIdDto of(Long id, String title, LocalDateTime createdAt, String createdBy)
    {
        return new NoteIdDto(id, title, createdAt, createdBy);
    }

    public static NoteIdDto from(Note entity) {
        return new NoteIdDto(
                entity.getId(),
                entity.getTitle(),
                entity.getCreatedAt(),
                entity.getCreatedBy()
        );
    }
}
