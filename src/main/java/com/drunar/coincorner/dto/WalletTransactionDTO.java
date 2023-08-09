package com.drunar.coincorner.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class WalletTransactionDTO {

    Long id;
    Long walletId;
    String walletName;
    BigDecimal previousBalance;
    BigDecimal amount;
    BigDecimal currentBalance;
    String operationType;
    LocalDateTime transactionDate;

}
