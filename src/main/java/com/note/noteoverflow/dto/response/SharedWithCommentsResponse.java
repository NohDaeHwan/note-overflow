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
        boolean likeCheck,
        boolean followCheck,
        LocalDateTime createdAt,
        NoteResponse noteResponse,
        Set<SharedNoteCommentResponse> sharedNoteCommentResponses,
        Set<LikeNoteResponse> likeNoteResponses
) {

    public static SharedWithCommentsResponse of(SharedWithCommentsDto sharedWithCommentsDto, boolean likeCheck, boolean followCheck) {
        return new SharedWithCommentsResponse(
                sharedWithCommentsDto.id(),
                sharedWithCommentsDto.viewCount(),
                sharedWithCommentsDto.likeCount(),
                likeCheck,
                followCheck,
                sharedWithCommentsDto.createdAt(),
                NoteResponse.from(sharedWithCommentsDto.noteDto()),
                sharedWithCommentsDto.sharedNoteCommentDtos().stream()
                                .map(SharedNoteCommentResponse::from)
                                .collect(Collectors.toCollection(LinkedHashSet::new)),
                sharedWithCommentsDto.likeNoteDtos().stream()
                                .map(LikeNoteResponse::from)
                                .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
