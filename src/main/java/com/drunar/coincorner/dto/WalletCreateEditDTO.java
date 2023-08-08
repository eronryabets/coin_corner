package com.drunar.coincorner.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class WalletCreateEditDTO {

    String walletName;
    String walletType;
    String currency;
    Long ownerId;
    BigDecimal balance;

}
