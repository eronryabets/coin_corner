package com.drunar.coincorner.database.repository;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.repository.filter.FilterUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        QuerydslPredicateExecutor<User>{

    @Query(value = "select u from User u ",
    countQuery = "select  count (distinct  u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);

    Optional<User> findUserByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
    @Modifying
    void updateFailedAttempts(int failAttempts, String email);

    @Query("SELECT c FROM User c WHERE c.email = ?1")
    Optional<User> findByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);

}
