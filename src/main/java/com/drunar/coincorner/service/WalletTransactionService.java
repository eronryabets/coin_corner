package com.drunar.coincorner.service;

import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.database.repository.WalletTransactionRepository;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import com.drunar.coincorner.mapper.WalletMapper;
import com.drunar.coincorner.mapper.WalletTransactionMapper;
import com.drunar.coincorner.util.predicateBuilder.WalletTrPredicateBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletTransactionMapper walletTransactionMapper;
    private final WalletMapper walletMapper;

    @Cacheable(value = "transactions", key = "#filter.hashCode() + #pageable.hashCode()")
    public Page<WalletTransactionDTO> findAll(WalletTransactionFilter filter, Pageable pageable) {
        Predicate predicate = WalletTrPredicateBuilder.buildPredicate(filter);

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("transactionDate").descending())
        );
        
        return walletTransactionRepository.findAll(predicate, pageable)
                .map(walletTransactionMapper::walletTransactionToWalletTransactionDTO);
    }

    @Cacheable(value = "transactions", key = "'findAll'")
    public List<WalletTransactionDTO> findAll() {
        return walletTransactionRepository.findAll().stream()
                .map(walletTransactionMapper::walletTransactionToWalletTransactionDTO).toList();
    }

    @Cacheable(value = "transactions", key = "'findAllByWallet' + #walletDTO.id")
    public Optional<List<WalletTransactionDTO>> findAllByWallet(WalletReadDTO walletDTO) {
        return walletTransactionRepository.findAllByWallet(walletMapper.walletReadDTOToWallet(walletDTO))
                .map(walletTr -> walletTr.stream()
                        .map(walletTransactionMapper::walletTransactionToWalletTransactionDTO)
                        .collect(Collectors.toList()));
    }


    @Transactional
    @CacheEvict(value = "transactions", allEntries = true)
    public WalletTransactionDTO create(WalletTransactionDTO walletTDTO) {
        return Optional.of(walletTDTO)
                .map(walletTransactionMapper::walletTransactionDTOToWallet)
                .map(it -> {
                    it.setCurrentBalance(it.getPreviousBalance().add(it.getAmount()));
                    return it;
                })
                .map(walletTransactionRepository::saveAndFlush)
                .map(walletTransactionMapper::walletTransactionToWalletTransactionDTO)
                .orElseThrow();
    }


}
