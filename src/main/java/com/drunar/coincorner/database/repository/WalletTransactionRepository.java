package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.database.repository.filter.FilterWalletTransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WalletTransactionRepository extends
        JpaRepository<WalletTransaction, Long>,
        FilterWalletTransactionRepository,
        QuerydslPredicateExecutor<WalletTransaction> {


    Optional<List<WalletTransaction>> findAllByWallet(Wallet wallet);

    Page<WalletTransaction> findAllBy(Pageable pageable);

}
