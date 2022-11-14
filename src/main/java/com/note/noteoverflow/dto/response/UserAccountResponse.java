package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.FollowingDto;
import com.note.noteoverflow.dto.UserAccountDto;

import java.util.List;

public record UserAccountResponse(
        Long id,
        String userEmail,
        String nickname,
        String userPhone,
        int sharedCount,
        String userImage,
        List<FollowingDto> followResponses,
        int follower,
        List<FollowingDto> followingResponses,
        int following
) {

    public static UserAccountResponse of(Long id, String userEmail, String nickname, String userPhone, int sharedCount,
                                         String userImage, List<FollowingDto> followResponses, List<FollowingDto> following) {
        return new UserAccountResponse(id, userEmail, nickname, userPhone, sharedCount, userImage,
                followResponses, 0, following, 0);
    }

    public static UserAccountResponse from(UserAccountDto dto, List<FollowingDto> following) {
        return new UserAccountResponse(
                dto.id(),
                dto.userEmail(),
                dto.nickname(),
                dto.userPhone(),
                dto.sharedCount(),
                dto.userImage(),
                dto.follow(),
                dto.followCount(),
                following,
                following.size()
        );
    }

}
