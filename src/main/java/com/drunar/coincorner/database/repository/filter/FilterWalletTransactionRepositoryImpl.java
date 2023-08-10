package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.database.querydsl.QPredicates;
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
        var predicate = QPredicates.builder()
                .add(filter.getOperationType(), walletTransaction.operationType::eq)
                .add(filter.getAmount(), walletTransaction.amount::eq)
                .add(filter.getTransactionDateIn(), walletTransaction.transactionDate::in)
                .add(filter.getTransactionDateAfter(), walletTransaction.transactionDate::after)
                .add(filter.getTransactionDateBefore(), walletTransaction.transactionDate::before)
                .add(filter.getTransactionDateStart(), walletTransaction.transactionDate::goe)
                .add(filter.getTransactionDateEnd(), walletTransaction.transactionDate::loe)
                .build();

        return new JPAQuery<Wallet>(entityManager)
                .select(walletTransaction)
                .from(walletTransaction)
                .where(predicate)
                .fetch();
    }


}
