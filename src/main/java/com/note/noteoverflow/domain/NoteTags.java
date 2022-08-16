package com.note.noteoverflow.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
		@Index(columnList = "tag"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@Entity
public class NoteTags extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@ManyToOne(optional = false)
	private Note note; // 노트 (ID)

	@Setter
	@Column(nullable = false, length = 50)
	private String tag;

	protected NoteTags() {}

	private NoteTags(Note note, String tag) {
		this.note = note;
		this.tag = tag;
	}

	protected static NoteTags of(Note note, String tag) {
		return new NoteTags(note, tag);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof NoteTags that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
