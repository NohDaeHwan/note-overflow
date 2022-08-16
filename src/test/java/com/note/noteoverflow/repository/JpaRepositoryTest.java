package com.note.noteoverflow.repository;

import com.note.noteoverflow.config.JpaConfig;
import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.domain.type.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final NoteRepository noteRepository;
    private final NoteTagsRepository noteTagsRepository;
    private final UserAccountRepository userAccountRepository;

    JpaRepositoryTest(@Autowired NoteRepository noteRepository,
                      @Autowired NoteTagsRepository noteTagsRepository,
                      @Autowired UserAccountRepository userAccountRepository) {
        this.noteRepository = noteRepository;
        this.noteTagsRepository = noteTagsRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Note> notes = noteRepository.findAll();

        // Then
        assertThat(notes).isNotNull().hasSize(119);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = noteRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("eo@gmail.com", "pw", null, null, RoleType.USER, 0));
        Note note = Note.of("new title", "new mCategory", "new sCategory", "new content", false, userAccount);

        // When
        noteRepository.save(note);

        // Then
        assertThat(noteRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Note note = noteRepository.findById(1L).orElseThrow();
        String updatedMCategory = "SpringBoot";
        note.setMCategory(updatedMCategory);

        // When
        Note savedNote = noteRepository.saveAndFlush(note);

        // Then
        assertThat(savedNote).hasFieldOrPropertyWithValue("mCategory", updatedMCategory);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Note note = noteRepository.findById(1L).orElseThrow();
        long previousNoteCount = noteRepository.count();
        long previousNoteTagCount = noteTagsRepository.count();
        int deletedTagSize = note.getNoteTags().size();

        // When
        noteRepository.delete(note);

        // Then
        assertThat(noteRepository.count()).isEqualTo(previousNoteCount - 1);
        assertThat(noteTagsRepository.count()).isEqualTo(previousNoteTagCount - deletedTagSize);
    }

}
