package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.NoteTagsDto;

import java.time.LocalDateTime;

public record NoteTagsResponse(
        Long id,
        Long noteId,
        String tag,
        LocalDateTime createdAt
) {

    public static NoteTagsResponse of(Long id, Long noteId, String tag, LocalDateTime createdAt) {
        return new NoteTagsResponse(id, noteId, tag, createdAt);
    }

    public static NoteTagsResponse from(NoteTagsDto dto) {
        return new NoteTagsResponse(
                dto.id(),
                dto.noteId(),
                dto.tag(),
                dto.createdAt()
        );
    }

}
