package com.drunar.coincorner.service;

import com.drunar.coincorner.database.repository.NoteRepository;
import com.drunar.coincorner.dto.NoteDTO;
import com.drunar.coincorner.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public Optional<List<NoteDTO>> findAllByUser(NoteDTO note){
        return noteRepository.findAllByUserId(note.getUserId())
                .map(notes -> notes.stream().map(noteMapper::noteToNoteDTO)
                        .collect(Collectors.toList()));

    }

    @Transactional
    public NoteDTO create(NoteDTO note){
        return Optional.of(note)
                .map(noteMapper::noteDTOToNote)
                .map(noteRepository::save)
                .map(noteMapper::noteToNoteDTO)
                .orElseThrow();
    }




}
