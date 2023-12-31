package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.repository.filter.FilterWalletRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends
        JpaRepository<Wallet, Long>,
        FilterWalletRepository,
        RevisionRepository<Wallet, Long, Integer>,
        QuerydslPredicateExecutor<Wallet> {

    Optional<List<Wallet>> findAllByOwnerUser(User user);

    Optional<List<Wallet>> findAllByOwnerUserId(Long id);

    Page<Wallet> findAllBy(Pageable pageable);

}
