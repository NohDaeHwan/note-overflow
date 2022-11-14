package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.QNote;
import com.note.noteoverflow.dto.NoteCategoryDto;
import com.note.noteoverflow.repository.querydsl.NoteRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

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

    @Query(nativeQuery = true, value =
            "SELECT m_category " +
                    "FROM note " +
                    "WHERE user_account_id = :userId AND sharing = :sharing " +
                    "GROUP BY m_category")
    List<Object[]> findGroupBymCategory(@Param("userId") Long userId, @Param("sharing") boolean sharing);

    // 마이페이지 메인 카테고리, 서브 코테고리, 타이틀
    @Query(nativeQuery = true, value = "SELECT * FROM note WHERE user_account_id = :userId AND sharing = :sharing")
    List<Note> findPrivateNote(@Param("userId") Long userId, @Param("sharing") boolean sharing);

    @Query(value = "SELECT DISTINCT new com.note.noteoverflow.dto.NoteCategoryDto(n.mCategory) FROM Note n WHERE n.userAccount.id = :userId AND n.sharing = :sharing")
    List<NoteCategoryDto> findByUserAccountId(@Param("userId") Long userId, @Param("sharing") boolean sharing);
}
