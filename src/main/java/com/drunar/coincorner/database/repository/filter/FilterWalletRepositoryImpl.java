package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.util.predicateBuilder.WalletPredicateBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.drunar.coincorner.database.entity.QWallet.wallet;

@RequiredArgsConstructor
public class FilterWalletRepositoryImpl implements FilterWalletRepository{

    private final EntityManager entityManager;


    @Override
    public List<Wallet> findAllByFilter(WalletFilter filter) {
        Predicate predicate = WalletPredicateBuilder.buildPredicate(filter);

        return new JPAQuery<Wallet>(entityManager)
                .select(wallet)
                .from(wallet)
                .where(predicate)
                .fetch();
    }
}
