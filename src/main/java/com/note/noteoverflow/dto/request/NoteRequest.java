package com.note.noteoverflow.dto.request;

import com.note.noteoverflow.dto.NoteDto;
import com.note.noteoverflow.dto.UserAccountDto;

import java.util.*;
import java.util.stream.Collectors;

public record NoteRequest(
        Long id,
        String title,
        String mCategory,
        String sCategory,
        String content,
        boolean sharing,
        List<NoteTagsRequest> noteTagsRequests
) {

    public static NoteRequest of(Long id, String title, String mCategory, String sCategory, String content, List<NoteTagsRequest> noteTagsRequests)
    {
        return new NoteRequest(id, title, mCategory, sCategory, content, false, noteTagsRequests);
    }

    public NoteDto toDto(UserAccountDto userAccountDto) {
        return NoteDto.of(
                id,
                userAccountDto,
                noteTagsRequests.stream()
                        .map(NoteTagsRequest::toDto)
                        .collect(Collectors.toUnmodifiableList()),
                title,
                mCategory,
                sCategory,
                content,
                sharing
        );
}

}
