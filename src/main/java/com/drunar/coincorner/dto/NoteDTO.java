package com.drunar.coincorner.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class NoteDTO {

    Long id;

    @NotEmpty(message = "Title should be field in")
    String title;

    @NotEmpty(message = "Text should be field in")
    String text;

    LocalDateTime dateAdded;

    Long userId;

}
