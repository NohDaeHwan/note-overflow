package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
		@Index(columnList = "content"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class SharedNoteComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false, length = 500)
	private String content; // 내용

	@Setter
	@ManyToOne(optional = false)
	private Shared shared; // 공유 노트 (ID)

	@Setter
	@ManyToOne(optional = false)
	private UserAccount userAccount; // 회원 (ID)

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt; // 생성일시

	@CreatedBy
	@Column(nullable = false, updatable = false, length = 100)
	private String createdBy; // 생성자

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime modifiedAt; // 수정일시

	@LastModifiedBy
	@Column(nullable = false, length = 100)
	private String modifiedBy; // 수정자

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
