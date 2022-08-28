package com.note.noteoverflow.repository.querydsl;

import java.util.List;

public interface NoteRepositoryCustom {
    List<Long> findAllIds(List<Long> noteIds);
}
