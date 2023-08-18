package com.drunar.coincorner.util.predicateBuilder;

import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.database.querydsl.QPredicates;
import com.querydsl.core.types.Predicate;

import static com.drunar.coincorner.database.entity.QUser.user;

public class UserPredicateBuilder {
    public static Predicate buildPredicate(UserFilter filter) {
        return QPredicates.builder()
                .add(filter.getFirstname(), user.firstname::containsIgnoreCase)
                .add(filter.getLastname(), user.lastname::containsIgnoreCase)
                .add(filter.getBirthDateIn(), user.birthDate::in)
                .add(filter.getBirthDateAfter(), user.birthDate::after)
                .add(filter.getBirthDateBefore(), user.birthDate::before)
                .add(filter.getBirthDateRangeStart(), user.birthDate::goe)
                .add(filter.getBirthDateRangeEnd(), user.birthDate::loe)
                .add(filter.getRoles(),user.roles::contains)
                .build();
    }
}