package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.querydsl.QPredicates;
import com.drunar.coincorner.database.filter.WalletFilter;
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
        var predicate = QPredicates.builder()
                .add(filter.getWalletName(), wallet.walletName::containsIgnoreCase)
                .add(filter.getWalletType(), wallet.walletType::eq)
                .add(filter.getCurrency(), wallet.currency::eq)
                .add(filter.getOwnerUser(), wallet.ownerUser::eq)
                .build();

        return new JPAQuery<Wallet>(entityManager)
                .select(wallet)
                .from(wallet)
                .where(predicate)
                .fetch();
    }
}
