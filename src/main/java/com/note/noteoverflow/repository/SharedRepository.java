package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.QShared;
import com.note.noteoverflow.domain.Shared;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface SharedRepository extends
        JpaRepository<Shared, Long>,
        QuerydslPredicateExecutor<Shared>,
        QuerydslBinderCustomizer<QShared>
{
    Page<Shared> findByNote_IdIn(List<Long> noteIds, Pageable pageable);
    Optional<Shared> findByNote_Id(Long noteId);

    @Override
    default void customize(QuerydslBindings bindings, QShared root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.viewCount, root.likeCount, root.createdAt, root.createdBy);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

    @Query(nativeQuery = true, value = "SELECT * FROM shared WHERE note_id IN (:noteId)",
            countQuery = "SELECT count(*) FROM shared WHERE note_id IN (:noteId)")
    Page<Shared> findByQueryResult(@Param("noteId") List<Long> noteId, Pageable page);

    int deleteByNote_Id(Long noteId);

}
