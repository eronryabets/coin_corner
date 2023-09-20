package com.drunar.coincorner.service;

import com.drunar.coincorner.aop.LogTransaction;
import com.drunar.coincorner.database.entity.Wallet;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final UserMapper userMapper;

    @Override
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

    @Override
    public List<WalletReadDTO> findAll(){
        return walletRepository.findAll().stream()
                .map(walletMapper::walletToWalletReadDTO).toList();
    }

    @Override
    @PreAuthorize("(@walletServiceImpl.doesWalletExistAndBelongsToUser(#id, authentication.principal.user.id))" +
            " or hasAnyAuthority('ADMIN')")
    public Optional<WalletReadDTO> findById(Long id){
        return walletRepository.findById(id).map(walletMapper::walletToWalletReadDTO);
    }

    @Override
    public Optional<List<WalletReadDTO>> findAllByUser(UserReadDTO userDTO) {
        return walletRepository.findAllByOwnerUser(userMapper.userReadDTOtoUser(userDTO))
                .map(wallets -> wallets.stream()
                        .map(walletMapper::walletToWalletReadDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<WalletReadDTO>> findAllByUserDetails(CustomUserDetails customUser) {
        return walletRepository.findAllByOwnerUserId(customUser.getUser().getId())
                .map(wallets -> wallets.stream()
                        .map(walletMapper::walletToWalletReadDTO)
                        .collect(Collectors.toList()));

    }

    @Override
    @Transactional
    public WalletReadDTO create(WalletCreateEditDTO walletDTO){
        return Optional.of(walletDTO)
                .map(walletMapper::walletCreateEditDTOToWallet)
                .map(walletRepository::save)
                .map(walletMapper::walletToWalletReadDTO)
                .orElseThrow();
    }

    @Override
    @Transactional
    public Optional<WalletReadDTO> update(Long id, WalletCreateEditDTO walletDTO){
        return walletRepository.findById(id)
                .map(entity -> walletMapper.walletCreateEditDTOCopyToWallet(walletDTO, entity))
                .map(walletRepository::saveAndFlush)
                .map(walletMapper::walletToWalletReadDTO);
    }

    @Override
    @Transactional
    @LogTransaction
    public boolean updateBalance(Long walletId, BigDecimal amount) {
        return walletRepository.findById(walletId)
                .map(entity ->{
                    entity.setBalance(entity.getBalance().add(amount));
                    walletRepository.saveAndFlush(entity);

                    return true;
                }).orElse(false);

    }

    @Override
    @Transactional
    public boolean delete(Long id){
        return walletRepository.findById(id)
                .map(entity -> {
                    walletRepository.delete(entity);
                    walletRepository.flush();
                    return true;
                }).orElse(false);
    }

    @Override
    public boolean doesWalletExistAndBelongsToUser(Long walletId, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        return wallet.isPresent() && wallet.get().getOwnerUser().getId().equals(userId);
    }

}
