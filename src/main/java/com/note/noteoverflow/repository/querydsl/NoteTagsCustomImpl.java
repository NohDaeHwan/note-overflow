package com.note.noteoverflow.repository.querydsl;

import com.note.noteoverflow.domain.NoteTags;
import com.note.noteoverflow.domain.QNoteTags;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NoteTagsCustomImpl extends QuerydslRepositorySupport implements NoteTagsCustom {

    public NoteTagsCustomImpl() {
        super(NoteTags.class);
    }

    @Override
    public List<Long> findAllNoteIds(String searchKeyword) {
        QNoteTags noteTags = QNoteTags.noteTags;

        JPQLQuery<Long> query = from(noteTags)
                .select(noteTags.note.id)
                .where(noteTags.tag.like("%"+searchKeyword+"%"));
        return query.fetch();
    }

}
