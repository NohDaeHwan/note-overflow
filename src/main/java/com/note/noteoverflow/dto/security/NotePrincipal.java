package com.note.noteoverflow.dto.security;

import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.domain.type.RoleType;
import com.note.noteoverflow.dto.FollowDto;
import com.note.noteoverflow.dto.UserAccountDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public record NotePrincipal(
        Long id,
        String userEmail,
        String userPassword,
        String nickname,
        String userPhone,
        RoleType userRole,
        int sharedCount,
        String userImage,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements UserDetails {

    public static NotePrincipal of(Long id, String userEmail, String userPassword, String nickname, String userPhone,
                                   RoleType userRole, int sharedCount, String userImage, LocalDateTime createdAt, String createdBy,
                                   LocalDateTime modifiedAt, String modifiedBy) {
        return new NotePrincipal(
                id,
                userEmail,
                userPassword,
                nickname,
                userPhone,
                userRole,
                sharedCount,
                userImage,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    public static NotePrincipal from(UserAccount entity) {
        return NotePrincipal.of(
                entity.getId(),
                entity.getUserEmail(),
                entity.getUserPassword(),
                entity.getNickname(),
                entity.getUserPhone(),
                entity.getUserRole(),
                entity.getSharedCount(),
                entity.getUserImage(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                id,
                userEmail,
                userPassword,
                nickname,
                userPhone,
                userRole,
                sharedCount,
                userImage,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleType> roleTypes = Set.of(userRole);

        return roleTypes.stream()
                .map(RoleType::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
