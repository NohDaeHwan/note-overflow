package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.SharedWithCommentsDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record SharedWithCommentsResponse(
        Long id,
        int viewCount,
        int likeCount,
        LocalDateTime createdAt,
        NoteResponse noteResponse,
        Set<SharedNoteCommentResponse> sharedNoteCommentResponses
) {

    public static SharedWithCommentsResponse of(Long id, int viewCount, int likeCount, LocalDateTime createdAt, NoteResponse noteResponse,
                                                Set<SharedNoteCommentResponse> sharedNoteCommentResponses) {
        return new SharedWithCommentsResponse(id, viewCount, likeCount, createdAt, noteResponse, sharedNoteCommentResponses);
    }

    public static SharedWithCommentsResponse from(SharedWithCommentsDto dto) {
        return new SharedWithCommentsResponse(
                dto.id(),
                dto.viewCount(),
                dto.likeCount(),
                dto.createdAt(),
                NoteResponse.from(dto.noteDto()),
                dto.sharedNoteCommentDtos().stream()
                        .map(SharedNoteCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
