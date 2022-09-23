package com.note.noteoverflow.dto.request;

import com.note.noteoverflow.dto.NoteTagsDto;

public record NoteTagsRequest(
        String tag
) {
    public static NoteTagsRequest of(String tag) {
        return new NoteTagsRequest(tag);
    }

    public NoteTagsDto toDto() {
        return NoteTagsDto.of(tag);
    }

}
