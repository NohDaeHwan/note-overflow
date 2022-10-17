package com.note.noteoverflow.service;

import com.note.noteoverflow.domain.Note;
import com.note.noteoverflow.domain.Shared;
import com.note.noteoverflow.domain.UserAccount;
import com.note.noteoverflow.domain.type.RoleType;
import com.note.noteoverflow.dto.SharedDto;
import com.note.noteoverflow.dto.SharedWithCommentsDto;
import com.note.noteoverflow.repository.NoteRepository;
import com.note.noteoverflow.repository.NoteTagsRepository;
import com.note.noteoverflow.repository.SharedRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 공유 노트")
@ExtendWith(MockitoExtension.class)
class SharedServiceTest {

    @InjectMocks
    private SharedService sut;

    @Mock
    private SharedRepository sharedRepository;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteTagsRepository noteTagsRepository;

    /*@DisplayName("검색어 없이 게시글을 검색하면, 게시글 리스트를 반환하다")
    @Test
    void givenNoSearchParameters_whenSearchingNotes_thenReturnsNotePage() {
        // Given
        List<Long> expectedNoteIds = List.of(1L, 2L, 3L);
        Pageable pageable = Pageable.ofSize(20);
        given(sharedRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<SharedDto> shareds = sut.searchSharedNote(null, pageable);

        // Then
        assertThat(shareds).isEmpty();
        then(sharedRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 리스트를 반환하다")
    @Test
    void givenSearchParameters_whenSearchingNotes_thenReturnsNotePage() {
        // Given
        String searchKeyword = "spring";
        List<Long> expectedNoteIds = List.of(1L, 2L, 3L);
        Pageable pageable = Pageable.ofSize(20);
        given(noteTagsRepository.findAllNoteIds(searchKeyword)).willReturn(expectedNoteIds);
        given(noteRepository.findAllIds(expectedNoteIds)).willReturn(expectedNoteIds);
        given(sharedRepository.findByNote_IdIn(expectedNoteIds, pageable)).willReturn(Page.empty());

        // When
        Page<SharedDto> shareds = sut.searchSharedNote(searchKeyword, pageable);

        // Then
        assertThat(shareds).isEmpty();
        then(noteTagsRepository).should().findAllNoteIds(searchKeyword);
        then(noteRepository).should().findAllIds(expectedNoteIds);
        then(sharedRepository).should().findByNote_IdIn(expectedNoteIds, pageable);
    }

    @DisplayName("노트 ID로 조회하면, 댓글 달린 노트를 반환한다.")
    @Test
    void givenNoteId_whenSearchingNoteWithComments_thenReturnsNoteWithComments() {
        // Given
        Long noteId = 1L;
        Shared sharedNote = createSharedNote();
        given(sharedRepository.findByNote_Id(noteId)).willReturn(Optional.of(sharedNote));

        // When
        SharedWithCommentsDto dto = sut.getSharedWithComments(noteId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("viewCount", sharedNote.getViewCount())
                .hasFieldOrPropertyWithValue("likeCount", sharedNote.getLikeCount());
        then(sharedRepository).should().findByNote_Id(noteId);
    }

    private UserAccount createUserAccount() {
        UserAccount userAccount = UserAccount.of(
                "eoghks@mail.com",
                "password",
                "Eoghks",
                "01011112222",
                RoleType.USER,
                1
        );
        ReflectionTestUtils.setField(userAccount, "id", 1L);
        return userAccount;
    }

    private Shared createSharedNote() {
        Shared shared = Shared.of(
                createUserAccount(),
                createNote(),
                780,
                340
        );
        ReflectionTestUtils.setField(shared, "id", 1L);
        return shared;
    }

    private Note createNote() {
        Note note = Note.of(
                "title",
                "mCategory",
                "sCategory",
                "content",
                true,
                createUserAccount()
        );
        ReflectionTestUtils.setField(note, "id", 1L);
        return note;
    }*/

}
