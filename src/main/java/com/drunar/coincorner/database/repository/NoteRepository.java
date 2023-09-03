package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.Note;
import com.drunar.coincorner.database.repository.filter.FilterNoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>,
        FilterNoteRepository,
        QuerydslPredicateExecutor<Note> {

    Page<Note> findAllBy(Pageable pageable);
    Optional<List<Note>> findAllByUserId(Long id);


}
