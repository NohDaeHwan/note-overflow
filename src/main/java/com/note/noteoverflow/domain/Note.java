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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
		@Index(columnList = "title"),
		@Index(columnList = "mCategory"),
		@Index(columnList = "sCategory"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(nullable = false)
	private String title; // 제목

	@Setter
	@Column(nullable = false, length = 50)
	private String mCategory; // 메인 카테고리

	@Setter
	@Column(nullable = false, length = 50)
	private String sCategory; // 서브 카테고리

	@Setter
	@Column(nullable = false, length = 10000)
	private String content; // 내용

	@Setter
	@Column(nullable = false)
	private boolean sharing; // 공유 여부

	@Setter
	@ManyToOne(optional = false)
	private UserAccount userAccount; // 회원 (ID)

	@ToString.Exclude
	@OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
	private final Set<NoteTags> noteTags = new LinkedHashSet<>(); // 태그 (ID)

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

	protected Note() {}

	private Note(String title, String mCategory, String sCategory, String content, boolean sharing, UserAccount userAccount) {
		this.title = title;
		this.mCategory = mCategory;
		this.sCategory = sCategory;
		this.content = content;
		this.sharing = sharing;
		this.userAccount = userAccount;
	}

	public static Note of(String title, String mCategory, String sCategory, String content, boolean sharing, UserAccount userAccount) {
		return new Note(title, mCategory, sCategory, content, sharing, userAccount);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Note that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
