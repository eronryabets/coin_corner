package com.drunar.coincorner.service;

import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.database.repository.WalletTransactionRepository;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.mapper.UserMapper;
import com.drunar.coincorner.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
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
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletMapper walletMapper;
    private final UserMapper userMapper;

    public List<WalletReadDTO> findAll(){
        //TODO: filter, pageable
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
