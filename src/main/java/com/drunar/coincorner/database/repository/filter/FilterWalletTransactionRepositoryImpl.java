package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.util.predicateBuilder.WalletTrPredicateBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.drunar.coincorner.database.entity.QWalletTransaction.walletTransaction;

@RequiredArgsConstructor
public class FilterWalletTransactionRepositoryImpl implements FilterWalletTransactionRepository {

    private final EntityManager entityManager;

    @Override
    public List<WalletTransaction> findAllByFilter(WalletTransactionFilter filter) {
        Predicate predicate = WalletTrPredicateBuilder.buildPredicate(filter);
        return new JPAQuery<Wallet>(entityManager)
                .select(walletTransaction)
                .from(walletTransaction)
                .where(predicate)
                .fetch();
    }


}
