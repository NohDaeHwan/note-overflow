package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(name = "like_note")
@Entity
public class LikeNote extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "shared_note_id")
    @ManyToOne(optional = false)
    private Shared sharedNote; // 공유 노트 (ID)

    @Setter
    @Column(name = "like_id")
    private Long likeId; // 좋아요 ID

    @Setter
    @Column(name="like_user_email", nullable = false, length = 100)
    private String likeUserEmail; // 좋아요 이메일

    @Setter
    @Column(name="like_nickname", nullable = false, length = 100)
    private String likeNickname; // 좋아요 닉네임

    protected LikeNote() {}

    private LikeNote(Shared sharedNote, Long likeId, String likeUserEmail, String likeNickname) {
        this.sharedNote = sharedNote;
        this.likeId = likeId;
        this.likeUserEmail = likeUserEmail;
        this.likeNickname = likeNickname;
    }

    public static LikeNote of(Shared sharedNote, Long likeId, String likeUserEmail, String likeNickname) {
        return new LikeNote(sharedNote, likeId, likeUserEmail, likeNickname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeNote likeNote)) return false;
        return id != null && id.equals(likeNote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
