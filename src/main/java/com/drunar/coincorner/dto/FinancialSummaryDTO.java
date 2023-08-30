package com.drunar.coincorner.dto;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.entity.WalletTransaction;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class FinancialSummaryDTO {

    Long walletId;
    WalletTransaction.OperationType operationType;
    Wallet.Currency currency;
    BigDecimal transactionAmount;
    BigDecimal incomeAmount;
    BigDecimal expenseAmount;
    LocalDateTime dateStart;
    LocalDateTime dateEnd;

}
