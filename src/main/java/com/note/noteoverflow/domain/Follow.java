package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(name = "follow")
@Entity
public class Follow extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "user_account_id")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 회원 (ID)

    @Setter
    @Column(name = "follow_id", nullable = false)
    private Long followId; // 팔로우 ID

    protected Follow() {}

    private Follow(UserAccount userAccount, Long followId) {
        this.userAccount = userAccount;
        this.followId = followId;
    }

    public static Follow of(UserAccount userAccount, Long followId) {
        return new Follow(userAccount, followId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Follow follow)) return false;
        return id != null && id.equals(follow.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
