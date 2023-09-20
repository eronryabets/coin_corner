package com.drunar.coincorner.service;

import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface  WalletService {

    Page<WalletReadDTO> findAll(WalletFilter filter, Pageable pageable);

    List<WalletReadDTO> findAll();

    Optional<WalletReadDTO> findById(Long id);

    Optional<List<WalletReadDTO>> findAllByUser(UserReadDTO userDTO);

    Optional<List<WalletReadDTO>> findAllByUserDetails(CustomUserDetails customUser);

    WalletReadDTO create(WalletCreateEditDTO walletDTO);

    Optional<WalletReadDTO> update(Long id, WalletCreateEditDTO walletDTO);

    boolean updateBalance(Long walletId, BigDecimal amount);

    boolean delete(Long id);

    boolean doesWalletExistAndBelongsToUser(Long walletId, Long userId);

}
