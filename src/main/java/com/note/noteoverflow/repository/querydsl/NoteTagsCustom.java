package com.note.noteoverflow.repository.querydsl;

import java.util.List;

public interface NoteTagsCustom {
    List<Long> findAllNoteIds(String searchKeyword);
}
