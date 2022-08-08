package com.note.noteoverflow.domain;

import java.time.LocalDateTime;

public class SharedNote {

	private Long id;

	private int viewCount; // 조회수

	private int likeCount; // 좋아요

	private User user; // 회원 (ID)

	private PrivateNote privateNote; // 노트 (ID)

	private LocalDateTime createdAt; // 생성일시

	private String createdBy; // 생성자

	private LocalDateTime modifiedAt; // 수정일시

	private String modifiedBy; // 수정자
	
}
