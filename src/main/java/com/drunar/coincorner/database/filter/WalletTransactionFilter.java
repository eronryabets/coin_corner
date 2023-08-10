package com.drunar.coincorner.database.filter;

import com.drunar.coincorner.database.entity.WalletTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Value
@AllArgsConstructor
public class WalletTransactionFilter {
    WalletTransaction.OperationType operationType;
    BigDecimal amount;
    LocalDateTime transactionDateIn;
    LocalDateTime transactionDateAfter;
    LocalDateTime transactionDateBefore;
    LocalDateTime transactionDateStart;
    LocalDateTime transactionDateEnd;
}
