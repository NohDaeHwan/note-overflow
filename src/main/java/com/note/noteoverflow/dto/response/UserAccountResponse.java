package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.UserAccountDto;

public record UserAccountResponse(
        Long id,
        String userEmail,
        String nickname,
        String userPhone,
        int sharedCount,
        int follower,
        int following
) {

    public static UserAccountResponse of(Long id, String userEmail, String nickname, String userPhone, int sharedCount) {
        return new UserAccountResponse(id, userEmail, nickname, userPhone, sharedCount, 0, 0);
    }

    public static UserAccountResponse from(UserAccountDto dto, int following) {
        return new UserAccountResponse(
                dto.id(),
                dto.userEmail(),
                dto.nickname(),
                dto.userPhone(),
                dto.sharedCount(),
                dto.followDtos().size(),
                following
        );
    }

}
