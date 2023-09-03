package com.drunar.coincorner.service;

import com.drunar.coincorner.database.repository.NoteRepository;
import com.drunar.coincorner.dto.NoteDTO;
import com.drunar.coincorner.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public Optional<NoteDTO> findById(Long id){
        return noteRepository.findById(id).map(noteMapper::noteToNoteDTO);
    }

    @Transactional
    public NoteDTO create(NoteDTO note){
        return Optional.of(note)
                .map( it-> {
                        it.setDateAdded(LocalDateTime.now());
                        return     noteMapper.noteDTOToNote(it);
                        })
                .map(noteRepository::save)
                .map(noteMapper::noteToNoteDTO)
                .orElseThrow();
    }

    @Transactional
    public boolean update(Long noteId,NoteDTO note){
        return noteRepository.findById(noteId)
                .map(entity -> {
                    entity.setTitle(note.getTitle());
                    entity.setText(note.getText());
                    entity.setDateAdded(LocalDateTime.now());
                    noteRepository.saveAndFlush(entity);
                    return true;
                }).orElse(false);
    }

    @Transactional
    public boolean delete(Long id){
        return noteRepository.findById(id)
                .map(entity -> {
                    noteRepository.delete(entity);
                    noteRepository.flush();
                    return true;
                }).orElse(false);
    }





}
