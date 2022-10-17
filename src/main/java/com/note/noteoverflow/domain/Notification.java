package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(name = "notification", indexes = {
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by")
})
@Entity
public class Notification extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "user_account_id")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 회원 (ID)

    @Setter
    @Column(name="note_id")
    private Long noteId; // 노트 ID

    @Setter
    @Column(nullable = false)
    private boolean reading; // 읽기 여부

    @Setter
    @Column(name="user_id", nullable = false, length = 100)
    private Long userId; // 아이디

    @Setter
    @Column(length = 100, nullable = false)
    private String nickname; // 이름

    @Setter
    @Column(length = 100, nullable = false)
    private String content; // 내용

    protected Notification() {}

    private Notification(UserAccount userAccount, Long noteId, boolean reading, Long userId, String nickname, String content) {
        this.userAccount = userAccount;
        this.noteId = noteId;
        this.reading = reading;
        this.userId = userId;
        this.nickname = nickname;
        this.content = content;
    }

    public static Notification of(UserAccount userAccount, Long noteId, boolean reading, Long userId, String nickname, String content) {
        return new Notification(userAccount, noteId, reading, userId, nickname, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
