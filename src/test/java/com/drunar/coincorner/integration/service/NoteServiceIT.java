package com.drunar.coincorner.integration.service;

import com.drunar.coincorner.dto.NoteDTO;
import com.drunar.coincorner.integration.IntegrationTestBase;
import com.drunar.coincorner.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class NoteServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Long NOTE_1 = 1L;

    private final NoteService noteService;

    @Test
    void findAllByUser(){
        Optional<List<NoteDTO>> result = noteService.findAllByUser(USER_1);
        assertTrue(result.isPresent());
        assertThat(result.get()).hasSize(2);
    }

    @Test
    void findById(){
        Optional<NoteDTO> result = noteService.findById(NOTE_1);
        assertTrue(result.isPresent());
        System.out.println(result.get());
        assertEquals(result.get().getId(), NOTE_1);
        assertEquals(result.get().getText(), "Text 1");
        assertEquals(result.get().getUserId(), USER_1);
    }

    @Test
    void create(){
        NoteDTO noteDTO = NoteDTO.builder()
                .title("Title1")
                .text("Text1")
                .userId(USER_1)
                .build();

        NoteDTO actualResult = noteService.create(noteDTO);
        System.out.println(actualResult);

        assertEquals(noteDTO.getTitle(), actualResult.getTitle());
        assertEquals(noteDTO.getText(), actualResult.getText());
        assertEquals(noteDTO.getUserId(), actualResult.getUserId());

    }

    @Test
    void update(){
        NoteDTO noteDTO = NoteDTO.builder()
                .title("Title1")
                .text("Text1")
                .userId(USER_1)
                .build();

        Optional<NoteDTO> actualResult = noteService.update(NOTE_1, noteDTO);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(note -> {
            assertEquals(noteDTO.getTitle(), note.getTitle());
            assertEquals(noteDTO.getText(), note.getText());
            assertEquals(noteDTO.getUserId(), note.getUserId());

        });
    }

    @Test
    void delete() {
        assertFalse(noteService.delete(-124L));
        assertTrue(noteService.delete(NOTE_1));
    }

}
