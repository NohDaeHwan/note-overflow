package com.note.noteoverflow.domain;

import java.time.LocalDateTime;

public class User {

	private Long id;

	private String userEmail; // 아이디

	private String userPassword; // 비밀번호

	private String userName; // 이름

	private String userPhone; // 전화번호

	private RoleType role; // (USER, ADMIN)

	private int sharedCount; // 노트 공유 수

	private LocalDateTime createdAt; // 생성일시

	private String createdBy; // 생성자

	private LocalDateTime modifiedAt; // 수정일시

	private String modifiedBy; // 수정자

}
