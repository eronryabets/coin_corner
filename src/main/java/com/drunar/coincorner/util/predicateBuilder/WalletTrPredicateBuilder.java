package com.drunar.coincorner.util.predicateBuilder;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.database.querydsl.QPredicates;
import com.querydsl.core.types.Predicate;

import static com.drunar.coincorner.database.entity.QWalletTransaction.walletTransaction;

public class WalletTrPredicateBuilder {
    public static Predicate buildPredicate(WalletTransactionFilter filter) {
        return buildPredicate(filter, null);
    }

    public static Predicate buildPredicate(WalletTransactionFilter filter, Long userId) {
        return QPredicates.builder()
                .add(filter.getId(), walletTransaction.id::eq)
                .add(filter.getOperationType(), walletTransaction.operationType::eq)
                .add(filter.getCurrency(), walletTransaction.wallet.currency::eq)
                .add(filter.getAmount(), walletTransaction.amount::eq)
                .add(filter.getTransactionDateIn(), walletTransaction.transactionDate::in)
                .add(filter.getTransactionDateAfter(), walletTransaction.transactionDate::after)
                .add(filter.getTransactionDateBefore(), walletTransaction.transactionDate::before)
                .add(filter.getTransactionDateStart(), walletTransaction.transactionDate::goe)
                .add(filter.getTransactionDateEnd(), walletTransaction.transactionDate::loe)
                .add(filter.getWalletId(), walletTransaction.wallet.id::eq)
                .add(filter.getWalletName(), walletTransaction.wallet.walletName::containsIgnoreCase)
                .add(userId, userId != null ? walletTransaction.wallet.ownerUser.id::eq : null)
                .add(filter.getUsername(), walletTransaction.wallet.ownerUser.username::containsIgnoreCase)
                .build();
    }
}
