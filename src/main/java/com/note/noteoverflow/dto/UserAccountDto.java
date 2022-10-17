package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.domain.type.RoleType;

import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String userEmail,
        String userPassword,
        String nickname,
        String userPhone,
        RoleType userRole,
        int sharedCount,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static UserAccountDto of(Long id, String userEmail, String userPassword, String nickname, String userPhone,
                                    RoleType userRole, int sharedCount, LocalDateTime createdAt, String createdBy,
                                    LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(id, userEmail, userPassword, nickname, userPhone, userRole, sharedCount, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto of(Long id, String userEmail, String userPassword, String nickname, String userPhone,
                                    RoleType userRole, int sharedCount) {
        return new UserAccountDto(id, userEmail, userPassword, nickname, userPhone, userRole, sharedCount, null, null, null, null);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getId(),
                entity.getUserEmail(),
                entity.getUserPassword(),
                entity.getNickname(),
                entity.getUserPhone(),
                entity.getUserRole(),
                entity.getSharedCount(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(userEmail, userPassword, nickname, userPhone, userRole, sharedCount);
    }
}
