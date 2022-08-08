package com.note.noteoverflow.domain;

import java.time.LocalDateTime;

public class PrivateNote {

	private Long id;

	private String title; // 제목

	private String mCategory; // 메인 카테고리

	private String sCategory; // 서브 카테고리

	private String content; // 내용

	private boolean sharing; // 공유 여부

	private User user; // 회원 (ID)

	private Tags tag; // 태그 (ID)

	private LocalDateTime createdAt; // 생성일시

	private String createdBy; // 생성자
	
	private LocalDateTime modifiedAt; // 수정일시

	private String modifiedBy; // 수정자
	
}
