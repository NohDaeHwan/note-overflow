package com.note.noteoverflow.service;

import com.note.noteoverflow.dto.TagListDto;
import com.note.noteoverflow.repository.NoteTagsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NoteTagsService {

    private final NoteTagsRepository noteTagsRepository;

    // 태그 리스트 출력
    @Transactional
    public Page<TagListDto> selectTagList(String tag, Pageable pageable) {
        return noteTagsRepository.findByTagList(tag, pageable);
    }

}
