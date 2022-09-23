package com.note.noteoverflow.repository.querydsl;

import com.note.noteoverflow.dto.TagListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteTagsCustom {
    List<Long> findAllNoteIds(String searchKeyword);

    List<String> findAllTags(List<Long> noteId, String searchKeyword);

    List<Long> findGroupByTag(String query);

    Page<TagListDto> findByTagList(String tag, Pageable pageable);

}
