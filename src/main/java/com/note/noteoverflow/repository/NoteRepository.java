package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.QNote;
import com.note.noteoverflow.repository.querydsl.NoteRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NoteRepository extends
        JpaRepository<Note, Long>,
        NoteRepositoryCustom,
        QuerydslPredicateExecutor<Note>,
        QuerydslBinderCustomizer<QNote>
{

    @Override
    default void customize(QuerydslBindings bindings, QNote root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.createdAt, root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}
