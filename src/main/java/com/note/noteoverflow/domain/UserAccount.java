package com.note.noteoverflow.domain;

import com.note.noteoverflow.domain.type.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(name = "user_account", indexes = {
		@Index(columnList = "user_email", unique = true),
		@Index(columnList = "shared_count"),
		@Index(columnList = "created_at"),
		@Index(columnList = "created_by")
})
@Entity
public class UserAccount extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Column(name="user_email", nullable = false, length = 100)
	private String userEmail; // 아이디

	@Setter
	@Column(name="user_password", nullable = false, length = 100)
	private String userPassword; // 비밀번호

	@Setter
	@Column(length = 100)
	private String nickname; // 이름

	@Setter
	@Column(name="user_phone", length = 11)
	private String userPhone; // 전화번호

	@Setter
	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
	private RoleType userRole; // (USER, ADMIN)

	@Setter
	@Column(name="shared_count")
	private int sharedCount; // 노트 공유 수

	protected UserAccount() {}

	private UserAccount(Long id, String userEmail, String userPassword, String nickname, String userPhone, RoleType userRole, int sharedCount) {
		this.id = id;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.nickname = nickname;
		this.userPhone = userPhone;
		this.userRole = userRole;
		this.sharedCount = sharedCount;
	}

	public static UserAccount of(Long id, String userEmail, String userPassword, String nickname, String userPhone, RoleType userRole, int sharedCount) {
		return new UserAccount(id, userEmail, userPassword, nickname, userPhone, userRole, sharedCount);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserAccount that)) return false;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
