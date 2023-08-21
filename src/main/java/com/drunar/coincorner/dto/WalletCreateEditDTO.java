package com.drunar.coincorner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class WalletCreateEditDTO {

    @NotEmpty(message = "Wallet name should be filled in")
    String walletName;

    String walletType;

    String currency;

    Long ownerId;

    @Min(value = 0, message = "Balance must be greater than or equal to 0")
    @NotNull(message = "Balance should be filled in")
    BigDecimal balance;

}
