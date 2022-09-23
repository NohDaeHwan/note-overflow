package com.note.noteoverflow.repository.querydsl;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepositoryCustom {
    List<Long> findAllIds(List<Long> noteIds);

    List<Long> findBySharing();
}
