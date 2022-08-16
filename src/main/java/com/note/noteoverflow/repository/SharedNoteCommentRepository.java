package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.SharedNoteComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedNoteCommentRepository extends JpaRepository<SharedNoteComment, Long> {
}
