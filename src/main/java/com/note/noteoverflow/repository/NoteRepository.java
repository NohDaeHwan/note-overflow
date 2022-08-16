package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
