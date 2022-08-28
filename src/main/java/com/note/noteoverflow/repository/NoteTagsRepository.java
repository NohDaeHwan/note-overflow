package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.NoteTags;
import com.note.noteoverflow.domain.QNoteTags;
import com.note.noteoverflow.repository.querydsl.NoteTagsCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NoteTagsRepository extends
        JpaRepository<NoteTags, Long>,
        NoteTagsCustom,
        QuerydslPredicateExecutor<NoteTags>,
        QuerydslBinderCustomizer<QNoteTags>
{

    @Override
    default void customize(QuerydslBindings bindings, QNoteTags root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.tag, root.createdAt, root.createdBy);
        bindings.bind(root.tag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}
