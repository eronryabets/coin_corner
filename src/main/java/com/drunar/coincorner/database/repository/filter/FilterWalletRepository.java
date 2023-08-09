package com.drunar.coincorner.database.repository.filter;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.filter.WalletFilter;

import java.util.List;

public interface FilterWalletRepository {

    List<Wallet> findAllByFilter(WalletFilter filter);
}
