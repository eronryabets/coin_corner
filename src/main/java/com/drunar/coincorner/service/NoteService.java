package com.drunar.coincorner.service;

import com.drunar.coincorner.database.filter.NoteFilter;
import com.drunar.coincorner.dto.NoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    Page<NoteDTO> findAll(NoteFilter filter, Pageable pageable);
    Optional<List<NoteDTO>> findAllByUser(Long userId);
    Optional<NoteDTO> findById(Long id);
    NoteDTO create(NoteDTO note);
    Optional<NoteDTO> update(Long noteId, NoteDTO note);
    boolean delete(Long id);

}
