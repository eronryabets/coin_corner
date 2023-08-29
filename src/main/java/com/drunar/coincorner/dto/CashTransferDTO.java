package com.drunar.coincorner.dto;

import com.drunar.coincorner.validation.annotations.WalletExists;
import lombok.Data;

import java.math.BigDecimal;

@Data
@WalletExists
public class CashTransferDTO {

    Long senderWalletId;
    Long recipientWalletId;
    BigDecimal amount;
}
