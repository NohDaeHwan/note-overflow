package com.note.noteoverflow.repository.querydsl;

import com.note.noteoverflow.domain.NoteTags;
import com.note.noteoverflow.domain.QNoteTags;
import com.note.noteoverflow.dto.TagListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

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

    @Override
    public List<String> findAllTags(List<Long> noteId, String searchKeyword) {
        QNoteTags noteTags = QNoteTags.noteTags;

        JPQLQuery<String> query = from(noteTags).distinct()
                .select(noteTags.tag)
                .where(noteTags.tag.like("%"+searchKeyword+"%"),
                        noteTags.note.id.in(noteId));
        return query.fetch();
    }

    @Override
    public List<Long> findGroupByTag(String searchKeyword) {
        QNoteTags noteTags = QNoteTags.noteTags;

        JPQLQuery<Long> query = from(noteTags).distinct()
                .select(noteTags.note.id)
                .where(noteTags.tag.like("%"+searchKeyword+"%"));
        return query.fetch();
    }

    @Override
    public Page<TagListDto> findByTagList(String tag, Pageable pageable) {
        QNoteTags noteTags = QNoteTags.noteTags;

        List<TagListDto> content = from(noteTags)
                .select(Projections.fields(TagListDto.class, noteTags.tag, noteTags.tag.count().as("tagCount")))
                .where(noteTags.tag.like("%"+tag+"%"))
                .groupBy(noteTags.tag)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<TagListDto> countQuery = from(noteTags)
                .select(Projections.fields(TagListDto.class, noteTags.tag, noteTags.tag.count().as("tagCount")))
                .where(noteTags.tag.like("%"+tag+"%"))
                .groupBy(noteTags.tag);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

}
