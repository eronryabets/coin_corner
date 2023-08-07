package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findWalletById(Long id);

}
