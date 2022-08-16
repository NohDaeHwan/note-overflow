package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.NoteTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteTagsRepository extends JpaRepository<NoteTags, Long> {
}
