package com.drunar.coincorner.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class WalletReadDTO {

    Long id;
    String walletName;
    String walletType;
    String currency;
    BigDecimal balance;
    Long ownerId;

}
