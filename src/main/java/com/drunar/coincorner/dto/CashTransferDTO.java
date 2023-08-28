package com.drunar.coincorner.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashTransferDTO {
    Long senderWalletId;
    Long recipientWalletId;
    BigDecimal amount;
}
