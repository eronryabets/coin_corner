package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.database.filter.WalletTransactionFilter;

import java.util.List;

public interface FilterWalletTransactionRepository {

    List<WalletTransaction> findAllByFilter(WalletTransactionFilter walletTrFilter);

}
