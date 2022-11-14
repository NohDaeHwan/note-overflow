package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.domain.type.RoleType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record UserAccountDto(
        Long id,
        String userEmail,
        String userPassword,
        String nickname,
        String userPhone,
        RoleType userRole,
        int sharedCount,
        String userImage,
        List<FollowingDto> follow,
        int followCount,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static UserAccountDto of(Long id, String userEmail, String userPassword, String nickname, String userPhone,
                                    RoleType userRole, int sharedCount, String userImage, List<FollowingDto> follow, int followCount,
                                    LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(id, userEmail, userPassword, nickname, userPhone, userRole, sharedCount, userImage,
                follow, followCount, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto of(Long id, String userEmail, String userPassword, String nickname, String userPhone,
                                    RoleType userRole, int sharedCount, String userImage, LocalDateTime createdAt, String createdBy,
                                    LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(id, userEmail, userPassword, nickname, userPhone, userRole, sharedCount, userImage, null,
                0, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity, List<FollowingDto> follow) {
        return new UserAccountDto(
                entity.getId(),
                entity.getUserEmail(),
                entity.getUserPassword(),
                entity.getNickname(),
                entity.getUserPhone(),
                entity.getUserRole(),
                entity.getSharedCount(),
                entity.getUserImage(),
                follow,
                follow.size(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
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
                entity.getUserImage(),
                null,
                entity.getFollows().size(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(userEmail, userPassword, nickname, userPhone, userRole, sharedCount, userImage);
    }
}
