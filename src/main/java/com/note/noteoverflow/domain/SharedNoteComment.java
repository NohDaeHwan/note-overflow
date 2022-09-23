package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(name = "shared_note_comment", indexes = {
		@Index(columnList = "content"),
		@Index(columnList = "created_at"),
		@Index(columnList = "created_by")
})
@Entity
public class SharedNoteComment extends  AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false, length = 500)
	private String content; // 내용

	@Setter
	@JoinColumn(name = "shared_id")
	@ManyToOne(optional = false)
	private Shared shared; // 공유 노트 (ID)

	@Setter
	@JoinColumn(name = "user_account_id")
	@ManyToOne(optional = false)
	private UserAccount userAccount; // 회원 (ID)

	protected SharedNoteComment() {}

	private SharedNoteComment(String content, Shared shared, UserAccount userAccount) {
		this.content = content;
		this.shared = shared;
		this.userAccount = userAccount;
	}

	public static SharedNoteComment of(String content, Shared shared, UserAccount userAccount) {
		return new SharedNoteComment(content, shared, userAccount);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SharedNoteComment that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
