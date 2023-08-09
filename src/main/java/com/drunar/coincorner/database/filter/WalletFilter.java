package com.drunar.coincorner.database.filter;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.entity.Wallet;

public record WalletFilter (String walletName,
                            Wallet.WalletType walletType,
                            Wallet.Currency currency,
                            User ownerUser){
}
