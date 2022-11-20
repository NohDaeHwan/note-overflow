package com.note.noteoverflow.dto.request;

public record JoinRequest(
        String userEmail,
        String userPassword,
        String userName,
        String userPhone
) {

    public static JoinRequest of(String userEmail, String userPassword, String userName, String userPhone)
    {
        return new JoinRequest(userEmail, userPassword, userName, userPhone);
    }

}
