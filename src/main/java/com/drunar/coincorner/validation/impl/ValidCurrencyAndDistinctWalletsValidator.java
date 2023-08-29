package com.drunar.coincorner.validation.impl;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.dto.CashTransferDTO;
import com.drunar.coincorner.validation.annotations.ValidCurrencyAndDistinctWallets;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidCurrencyAndDistinctWalletsValidator implements
        ConstraintValidator<ValidCurrencyAndDistinctWallets, CashTransferDTO> {

    private final WalletRepository walletRepository;

    @Override
    public boolean isValid(CashTransferDTO value, ConstraintValidatorContext context) {
        Optional<Wallet> recipientWallet = walletRepository.findById(value.getRecipientWalletId());
        Optional<Wallet> senderWallet = walletRepository.findById(value.getSenderWalletId());

        if (recipientWallet.isEmpty() || senderWallet.isEmpty()) {
            return false;
        }

        boolean currenciesMatch = recipientWallet.get().getCurrency().equals(senderWallet.get().getCurrency());

        return currenciesMatch && !value.getRecipientWalletId().equals(value.getSenderWalletId());
    }
}
