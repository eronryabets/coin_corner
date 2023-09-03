package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.Note;
import com.drunar.coincorner.database.filter.NoteFilter;

import java.util.List;

public interface FilterNoteRepository {

    List<Note> findAllByFilter(NoteFilter filter);
}
