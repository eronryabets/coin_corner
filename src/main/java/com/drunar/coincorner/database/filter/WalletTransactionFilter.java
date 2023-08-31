package com.drunar.coincorner.database.filter;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.entity.WalletTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class WalletTransactionFilter {
    Long id;
    WalletTransaction.OperationType operationType;
    Wallet.Currency currency;
    BigDecimal amount;
    LocalDateTime transactionDateIn;
    LocalDateTime transactionDateAfter;
    LocalDateTime transactionDateBefore;
    LocalDateTime transactionDateStart;
    LocalDateTime transactionDateEnd;
    Long walletId;
    String walletName;
    Long userId;
    String username;



}
