package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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

	@ToString.Exclude
	@OrderBy("createdAt DESC")
	@OneToMany(mappedBy = "shared", cascade = CascadeType.ALL)
	private final Set<SharedNoteComment> SharedNoteComments = new LinkedHashSet<>();

	protected Shared() {}

	private Shared(UserAccount userAccount, Note note, int viewCount, int likeCount) {
		this.userAccount = userAccount;
		this.note = note;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
	}

	public static Shared of(UserAccount userAccount, Note note, int viewCount, int likeCount) {
		return new Shared(userAccount, note, viewCount, likeCount);
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
