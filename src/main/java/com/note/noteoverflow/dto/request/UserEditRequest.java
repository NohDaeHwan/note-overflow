package com.note.noteoverflow.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserEditRequest(
        String userEmail,
        String userPhone,
        String nickname,
        MultipartFile image
) {

    public static UserEditRequest of(String userEmail, String userPhone, String nickname, MultipartFile image)
    {
        return new UserEditRequest(userEmail, userPhone, nickname, image);
    }

}
