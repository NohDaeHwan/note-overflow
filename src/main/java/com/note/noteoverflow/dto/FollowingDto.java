package com.note.noteoverflow.dto;

public record FollowingDto(
        Long followId,
        String followImage,
        String followUserEmail,
        String followNickname
) {
    public static FollowingDto of(Long followId, String followUserEmail,
                                  String followImage, String followNickname) {
        return new FollowingDto(followId, followImage, followUserEmail, followNickname);
    }

}
