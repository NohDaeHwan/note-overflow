package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
		@Index(columnList = "viewCount"),
		@Index(columnList = "likeCount"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@Entity
public class Shared extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	private int viewCount; // 조회수

	@Setter
	private int likeCount; // 좋아요

	@Setter
	@ManyToOne(optional = false)
	private UserAccount userAccount; // 회원 (ID)

	@Setter
	@ManyToOne(optional = false)
	private Note note; // 노트 (ID)

	protected Shared() {}

	private Shared(int viewCount, int likeCount, UserAccount userAccount, Note note) {
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.userAccount = userAccount;
		this.note = note;
	}

	public static Shared of(int viewCount, int likeCount, UserAccount userAccount, Note note) {
		return new Shared(viewCount, likeCount, userAccount, note);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Shared that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
