package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.Note;
import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.NoteDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class NoteMapperTest {

    @Autowired
    NoteMapper noteMapper;

    private Note note;
    private User user;

    @BeforeEach
    void init(){
        user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .birthDate(LocalDate.of(2000, 12, 12))
                .build();

        note = Note.builder()
                .id(1L)
                .text("Hello")
                .dateAdded(LocalDateTime.of(2023,9,2,12,12))
                .user(user)
                .build();
    }

    @Test
    void shouldProperlyMapNoteToNoteDTO() {
        NoteDTO dto = noteMapper.noteToNoteDTO(note);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(note.getId(), dto.getId());
        Assertions.assertEquals(note.getText(), dto.getText());
        Assertions.assertEquals(note.getDateAdded(), dto.getDateAdded());
        Assertions.assertEquals(note.getUser().getId(), dto.getUserId());

    }

    @Test
    void shouldProperlyMapNoteDTOToNote() {
        NoteDTO dto = noteMapper.noteToNoteDTO(note);
        Note note1 = noteMapper.noteDTOToNote(dto);

        Assertions.assertNotNull(note1);
        Assertions.assertEquals(note.getId(), dto.getId());
        Assertions.assertEquals(note.getText(), dto.getText());
        Assertions.assertEquals(note.getDateAdded(), dto.getDateAdded());
        Assertions.assertEquals(note.getUser().getId(), dto.getUserId());

    }

}
