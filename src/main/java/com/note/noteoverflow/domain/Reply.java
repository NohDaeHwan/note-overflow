package com.note.noteoverflow.domain;

import java.time.LocalDateTime;

public class Reply {

	private int id;

	private String content; // 내용

	private SharedNote sharedNote; // 공유 노트 (ID)

	private User user; // 회원 (ID)

	private LocalDateTime createdAt; // 생성일시

	private String createdBy; // 생성자

	private LocalDateTime modifiedAt; // 수정일시

	private String modifiedBy; // 수정자

}
