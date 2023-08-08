package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<List<Wallet>> findAllByOwnerUser(User user);
}
