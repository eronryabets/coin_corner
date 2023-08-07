package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
}
