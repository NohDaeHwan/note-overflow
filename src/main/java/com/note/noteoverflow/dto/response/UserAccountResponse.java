package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.UserAccountDto;

public record UserAccountResponse(
        Long id,
        String userEmail,
        String nickname,
        String userPhone,
        int sharedCount
) {

    public static UserAccountResponse of(Long id, String userEmail, String nickname, String userPhone, int sharedCount) {
        return new UserAccountResponse(id, userEmail, nickname, userPhone, sharedCount);
    }

    public static UserAccountResponse from(UserAccountDto dto) {
        return new UserAccountResponse(
                dto.id(),
                dto.userEmail(),
                dto.nickname(),
                dto.userPhone(),
                dto.sharedCount()
        );
    }

}
