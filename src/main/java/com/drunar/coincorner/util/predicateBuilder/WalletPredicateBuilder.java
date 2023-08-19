package com.drunar.coincorner.util.predicateBuilder;

import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.database.querydsl.QPredicates;

import com.querydsl.core.types.Predicate;

import static com.drunar.coincorner.database.entity.QWallet.wallet;


public class WalletPredicateBuilder {

    public static Predicate buildPredicate(WalletFilter filter){
        return QPredicates.builder()
                .add(filter.getWalletName(), wallet.walletName::equalsIgnoreCase)
                .add(filter.getWalletType(), wallet.walletType::eq)
                .add(filter.getCurrency(), wallet.currency::eq)
                .add(filter.getOwnerUserId(), wallet.ownerUser.id::eq)
                .add(filter.getOwnerUsername(), wallet.ownerUser.username::eq)
                .build();
    }
}
