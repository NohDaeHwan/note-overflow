package com.note.noteoverflow.repository;

import com.note.noteoverflow.domain.Shared;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedRepository extends JpaRepository<Shared, Long> {
}
