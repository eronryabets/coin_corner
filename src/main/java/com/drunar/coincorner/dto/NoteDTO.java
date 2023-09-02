package com.drunar.coincorner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class NoteDTO {

    Long id;
    String text;
    LocalDateTime dateAdded;
    String username;
    Long userId;

}
