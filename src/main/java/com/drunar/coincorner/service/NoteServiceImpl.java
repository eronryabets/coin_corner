package com.drunar.coincorner.service;

import com.drunar.coincorner.database.filter.NoteFilter;
import com.drunar.coincorner.database.repository.NoteRepository;
import com.drunar.coincorner.dto.NoteDTO;
import com.drunar.coincorner.mapper.NoteMapper;
import com.drunar.coincorner.util.predicateBuilder.NotePredicateBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public Page<NoteDTO> findAll(NoteFilter filter, Pageable pageable){
        Predicate predicate = NotePredicateBuilder.buildPredicate(filter);

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("dateAdded").descending())
        );

        return noteRepository.findAll(predicate, pageable).map(noteMapper::noteToNoteDTO);

    }

    @Override
    public Optional<List<NoteDTO>> findAllByUser(Long userId){
        return noteRepository.findAllByUserId(userId)
                .map(notes -> notes.stream().map(noteMapper::noteToNoteDTO)
                        .collect(Collectors.toList()));

    }

    @Override
    public Optional<NoteDTO> findById(Long id){
        return noteRepository.findById(id).map(noteMapper::noteToNoteDTO);
    }

    @Override
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

    @Override
    @Transactional
    public Optional<NoteDTO> update(Long noteId, NoteDTO note) {
        return noteRepository.findById(noteId)
                .map(entity -> {
                    entity.setTitle(note.getTitle());
                    entity.setText(note.getText());
                    entity.setDateAdded(LocalDateTime.now());
                    return noteRepository.saveAndFlush(entity);
                })
                .map(noteMapper::noteToNoteDTO);
    }

    @Override
    @Transactional
    public boolean delete(Long id){
        return noteRepository.findById(id)
                .map(entity -> {
                    noteRepository.delete(entity);
                    return true;
                }).orElse(false);
    }





}
