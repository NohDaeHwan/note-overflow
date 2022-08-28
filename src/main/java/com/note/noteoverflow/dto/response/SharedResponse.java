package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.SharedDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record SharedResponse(
        Long id,
        Long noteId,
        String title,
        String content,
        Set<NoteTagsResponse> noteTagsResponses,
        int viewCount,
        int likeCount,
        LocalDateTime createdAt,
        String email,
        Long userId,
        String nickname
) {
    public static SharedResponse of(Long id, Long noteId, String title, String content, Set<NoteTagsResponse> noteTagsResponses, int viewCount,
                                    int likeCount, LocalDateTime createdAt, String email, Long userId, String nickname) {
        return new SharedResponse(id, noteId, title, content, noteTagsResponses, viewCount, likeCount, createdAt, email, userId, nickname);
    }

    public static SharedResponse from(SharedDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userEmail();
        }

        return new SharedResponse(
                dto.id(),
                dto.noteDto().id(),
                dto.noteDto().title(),
                dto.noteDto().content(),
                dto.noteDto().noteTagsDto().stream()
                        .map(NoteTagsResponse::from)
                                .collect(Collectors.toCollection(LinkedHashSet::new)),
                dto.viewCount(),
                dto.likeCount(),
                dto.createdAt(),
                dto.userAccountDto().userEmail(),
                dto.userAccountDto().id(),
                nickname
        );
    }

}
