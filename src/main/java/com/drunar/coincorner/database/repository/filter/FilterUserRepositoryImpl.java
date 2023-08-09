package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.querydsl.QPredicates;
import com.drunar.coincorner.database.filter.UserFilter;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.drunar.coincorner.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{

    private final EntityManager entityManager;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .add(filter.birthDateIn(), user.birthDate::in)
                .add(filter.birthDateAfter(), user.birthDate::after)
                .add(filter.birthDateBefore(), user.birthDate::before)
                .add(filter.birthDateRangeStart(), user.birthDate::goe)
                .add(filter.birthDateRangeEnd(), user.birthDate::loe)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
