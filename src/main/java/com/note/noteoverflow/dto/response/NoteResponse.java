package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.NoteDto;
import com.note.noteoverflow.dto.NoteTagsDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record NoteResponse(
        Long id,
        String title,
        String mCategory,
        String sCategory,
        String content,
        boolean sharing,
        LocalDateTime createdAt,
        String email,
        Long userId,
        String nickname,
        Set<NoteTagsResponse> noteTagsResponses
) {

    public static NoteResponse of(Long id, String title, String mCategory, String sCategory, String content,
                                  boolean sharing, LocalDateTime createdAt, String email, Long userId,
                                  String nickname, Set<NoteTagsResponse> noteTagsResponses)
    {
        return new NoteResponse(id, title, mCategory, sCategory, content, sharing, createdAt, email, userId, nickname, noteTagsResponses);
    }

    public static NoteResponse from(NoteDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userEmail();
        }

        return new NoteResponse(
                dto.id(),
                dto.title(),
                dto.mCategory(),
                dto.sCategory(),
                dto.content(),
                dto.sharing(),
                dto.createdAt(),
                dto.userAccountDto().userEmail(),
                dto.userAccountDto().id(),
                nickname,
                dto.noteTagsDto().stream()
                        .map(NoteTagsResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
