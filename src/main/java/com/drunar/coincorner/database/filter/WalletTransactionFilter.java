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
    LocalDateTime transactionDate;
    BigDecimal amount;
}
