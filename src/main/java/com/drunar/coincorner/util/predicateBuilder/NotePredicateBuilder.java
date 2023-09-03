package com.drunar.coincorner.util.predicateBuilder;

import com.drunar.coincorner.database.filter.NoteFilter;
import com.drunar.coincorner.database.querydsl.QPredicates;
import com.querydsl.core.types.Predicate;

import static com.drunar.coincorner.database.entity.QNote.note;

public class NotePredicateBuilder {

    public static Predicate buildPredicate(NoteFilter filter) {
        return QPredicates.builder()
                .add(filter.getTitle(), note.title::containsIgnoreCase)
                .add(filter.getUserId(), note.user.id::eq)
                .build();
    }
}
