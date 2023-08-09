package com.drunar.coincorner.database.filter;

import com.drunar.coincorner.database.entity.WalletTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletTransactionFilter (WalletTransaction.OperationType operationType,
                                       LocalDateTime transactionDate,
                                       BigDecimal amount){
}
