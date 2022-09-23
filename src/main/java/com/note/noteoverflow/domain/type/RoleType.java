package com.note.noteoverflow.domain.type;

import lombok.Getter;

public enum RoleType {
	USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

	@Getter
	private final String name;

	RoleType(String name) {
		this.name = name;
	}

}
