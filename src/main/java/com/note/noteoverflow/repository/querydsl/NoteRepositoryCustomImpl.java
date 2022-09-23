package com.note.noteoverflow.repository.querydsl;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.QNote;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class NoteRepositoryCustomImpl extends QuerydslRepositorySupport implements NoteRepositoryCustom {

    public NoteRepositoryCustomImpl() {
            super(Note.class);
    }

    @Override
    public List<Long> findAllIds(List<Long> noteIds) {
        QNote note = QNote.note;

        JPQLQuery<Long> query = from(note)
                .select(note.id)
                .where(note.sharing.eq(true), note.id.in(noteIds));
        return query.fetch();
    }

    @Override
    public List<Long> findBySharing() {
        QNote note = QNote.note;

        JPQLQuery<Long> query = from(note)
                .select(note.id)
                .where(note.sharing.eq(true));
        return query.fetch();
    }

}
