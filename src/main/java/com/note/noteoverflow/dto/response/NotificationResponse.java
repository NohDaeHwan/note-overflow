package com.note.noteoverflow.dto.response;

import com.note.noteoverflow.dto.NotificationDto;

import java.util.List;

public record NotificationResponse(
        List<NotificationDto> notificationDtos,
        int notificationReading
) {
    public static NotificationResponse of(List<NotificationDto> notificationDtos, int notificationReading) {
        return new NotificationResponse(notificationDtos, notificationReading);
    }

    public static NotificationResponse from(List<NotificationDto> notificationDtos, int notificationReading) {
        return new NotificationResponse(
                notificationDtos,
                notificationReading
        );
    }

}
