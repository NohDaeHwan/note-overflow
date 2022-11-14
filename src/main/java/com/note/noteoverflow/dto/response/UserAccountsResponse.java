package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.UserAccountDto;

public record UserAccountsResponse(
        Long id,
        String userEmail,
        String nickname,
        String userPhone,
        int sharedCount,
        String userImage
) {

    public static UserAccountsResponse of(Long id, String userEmail, String nickname, String userPhone, int sharedCount, String userImage) {
        return new UserAccountsResponse(id, userEmail, nickname, userPhone, sharedCount, userImage);
    }

    public static UserAccountsResponse from(UserAccountDto dto) {
        return new UserAccountsResponse(
                dto.id(),
                dto.userEmail(),
                dto.nickname(),
                dto.userPhone(),
                dto.sharedCount(),
                dto.userImage()
        );
    }

}
