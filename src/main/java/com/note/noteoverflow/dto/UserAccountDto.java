package com.note.noteoverflow.dto;

import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.domain.type.RoleType;

import java.time.LocalDateTime;
import java.util.HashSet;
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
        Set<FollowDto> followDtos,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static UserAccountDto of(Long id, String userEmail, String userPassword, String nickname, String userPhone,
                                    RoleType userRole, int sharedCount, Set<FollowDto> followDtos, LocalDateTime createdAt, String createdBy,
                                    LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(id, userEmail, userPassword, nickname, userPhone, userRole, sharedCount,
                followDtos, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto of(Long id, String userEmail, String userPassword, String nickname, String userPhone,
                                    RoleType userRole, int sharedCount, LocalDateTime createdAt, String createdBy,
                                    LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(id, userEmail, userPassword, nickname, userPhone, userRole, sharedCount, null,
                createdAt, createdBy, modifiedAt, modifiedBy);
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
                entity.getFollows().stream()
                        .map(FollowDto::from)
                        .collect(Collectors.toCollection(HashSet::new)),
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
