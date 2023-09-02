package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<List<Note>> findAllByUserId(Long id);

}
