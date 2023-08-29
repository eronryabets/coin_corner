package com.drunar.coincorner.util;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.dto.WalletTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class WalletTransactionEnricher {

    public static WalletTransactionDTO enrich(WalletTransactionDTO dto, WalletReadDTO wallet){
        dto.setCurrentBalance(wallet.getBalance().add(dto.getAmount()));
        dto.setOperationType(operationType(dto.getAmount()));
        dto.setTransactionDate(LocalDateTime.now());
        return dto;
    }

    public static WalletTransactionDTO buildTransaction(WalletReadDTO wallet, BigDecimal amount){

        return WalletTransactionDTO.builder()
                .walletId(wallet.getId())
                .walletName(wallet.getWalletName())
                .currency(Wallet.Currency.valueOf(wallet.getCurrency()))
                .previousBalance(wallet.getBalance())
                .amount(amount)
                .currentBalance(wallet.getBalance().add(amount))
                .operationType(operationType(amount))
                .transactionDate(LocalDateTime.now())
                .build();
    }

    private static String operationType(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) > 0){
            return String.valueOf(WalletTransaction.OperationType.INCOME);
        }else return String.valueOf(WalletTransaction.OperationType.EXPENSE);
    }
}
