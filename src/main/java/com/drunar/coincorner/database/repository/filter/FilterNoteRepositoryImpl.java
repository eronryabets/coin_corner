package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.Note;
import com.drunar.coincorner.database.filter.NoteFilter;
import com.drunar.coincorner.util.predicateBuilder.NotePredicateBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.drunar.coincorner.database.entity.QNote.note;

@RequiredArgsConstructor
public class FilterNoteRepositoryImpl implements FilterNoteRepository{

    private final EntityManager entityManager;

    @Override
    public List<Note> findAllByFilter(NoteFilter filter) {
        Predicate predicate = NotePredicateBuilder.buildPredicate(filter);

        return new JPAQuery<Note>(entityManager)
                .select(note)
                .from(note)
                .where(predicate)
                .fetch();
    }
}
