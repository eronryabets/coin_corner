package com.drunar.coincorner.util.predicateBuilder;

import com.drunar.coincorner.database.entity.QWalletTransaction;
import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.database.querydsl.QPredicates;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

public class WalletTrPredicateBuilder {
    public static Predicate buildPredicate(WalletTransactionFilter filter) {
        return buildPredicate(filter, null);
    }

    public static Predicate buildPredicate(WalletTransactionFilter filter, Long userId) {
        QWalletTransaction walletTransaction = QWalletTransaction.walletTransaction;

        Predicate predicate = QPredicates.builder()
                .add(filter.getId(), walletTransaction.id::eq)
                .add(filter.getOperationType(), walletTransaction.operationType::eq)
                .add(filter.getAmount(), walletTransaction.amount::eq)
                .add(filter.getTransactionDateIn(), walletTransaction.transactionDate::in)
                .add(filter.getTransactionDateAfter(), walletTransaction.transactionDate::after)
                .add(filter.getTransactionDateBefore(), walletTransaction.transactionDate::before)
                .add(filter.getTransactionDateStart(), walletTransaction.transactionDate::goe)
                .add(filter.getTransactionDateEnd(), walletTransaction.transactionDate::loe)
                .add(filter.getWalletId(), walletTransaction.wallet.id::eq)
                .add(filter.getWalletName(), walletTransaction.wallet.walletName::containsIgnoreCase)
                .add(filter.getUsername(), walletTransaction.wallet.ownerUser.username::containsIgnoreCase)
                .build();

        if (userId != null) {
            predicate = ExpressionUtils.and(predicate, walletTransaction.wallet.ownerUser.id.eq(userId));
        }
        if (filter.getUserId() != null){
            predicate = ExpressionUtils.and(predicate, walletTransaction.wallet.ownerUser.id.eq(filter.getUserId()));
        }

        return predicate;
    }
}

