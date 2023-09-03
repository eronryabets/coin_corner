package com.drunar.coincorner.database.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class NoteFilter {

    String title;
    Long userId;

}
