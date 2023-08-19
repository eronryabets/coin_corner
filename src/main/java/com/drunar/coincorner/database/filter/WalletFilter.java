package com.drunar.coincorner.database.filter;

import com.drunar.coincorner.database.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class WalletFilter {
    String walletName;
    Wallet.WalletType walletType;
    Wallet.Currency currency;
    Long ownerUserId;
    String ownerUsername;
}
