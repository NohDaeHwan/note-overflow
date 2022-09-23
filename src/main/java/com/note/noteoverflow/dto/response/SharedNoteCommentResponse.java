package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.SharedNoteCommentDto;

import java.time.LocalDateTime;

public record SharedNoteCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        Long userId
) {

    public static SharedNoteCommentResponse of(Long id, String content, LocalDateTime createdAt, String email,
                                               String nickname, Long userId) {
        return new SharedNoteCommentResponse(id, content, createdAt, email, nickname, userId);
    }

    public static SharedNoteCommentResponse from(SharedNoteCommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userEmail();
        }

        return new SharedNoteCommentResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().userEmail(),
                nickname,
                dto.userAccountDto().id()
        );
    }

}
