package com.drunar.coincorner.service;

import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.mapper.UserMapper;
import com.drunar.coincorner.mapper.WalletMapper;
import com.drunar.coincorner.util.predicateBuilder.WalletPredicateBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final UserMapper userMapper;

    public Page<WalletReadDTO> findAll(WalletFilter filter, Pageable pageable){
        Predicate predicate = WalletPredicateBuilder.buildPredicate(filter);

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id"))
        );

        return walletRepository.findAll(predicate, pageable)
                .map(walletMapper::walletToWalletReadDTO);
    }

    public List<WalletReadDTO> findAll(){
        return walletRepository.findAll().stream()
                .map(walletMapper::walletToWalletReadDTO).toList();
    }

    public Optional<WalletReadDTO> findById(Long id){
        return walletRepository.findById(id).map(walletMapper::walletToWalletReadDTO);
    }

    public Optional<List<WalletReadDTO>> findAllByUser(UserReadDTO userDTO) {
        return walletRepository.findAllByOwnerUser(userMapper.userDTOtoUser(userDTO))
                .map(wallets -> wallets.stream()
                        .map(walletMapper::walletToWalletReadDTO)
                        .collect(Collectors.toList()));
    }

    public Optional<List<WalletReadDTO>> findAllByUserDetails(CustomUserDetails user) {
        return walletRepository.findAllByOwnerUserId(user.getId())
                .map(wallets -> wallets.stream()
                        .map(walletMapper::walletToWalletReadDTO)
                        .collect(Collectors.toList()));

    }

    @Transactional
    public WalletReadDTO create(WalletCreateEditDTO walletDTO){
        return Optional.of(walletDTO)
                .map(walletMapper::walletCreateEditDTOToWallet)
                .map(walletRepository::save)
                .map(walletMapper::walletToWalletReadDTO)
                .orElseThrow();
    }

    @Transactional
    public Optional<WalletReadDTO> update(Long id, WalletCreateEditDTO walletDTO){
        return walletRepository.findById(id)
                .map(entity -> walletMapper.walletCreateEditDTOCopyToWallet(walletDTO, entity))
                .map(walletRepository::saveAndFlush)
                .map(walletMapper::walletToWalletReadDTO);
    }

    @Transactional
    public boolean delete(Long id){
        return walletRepository.findById(id)
                .map(entity -> {
                    walletRepository.delete(entity);
                    walletRepository.flush();
                    return true;
                }).orElse(false);
    }

}
