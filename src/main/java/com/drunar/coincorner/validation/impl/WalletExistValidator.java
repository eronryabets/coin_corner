package com.drunar.coincorner.validation.impl;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.dto.CashTransferDTO;
import com.drunar.coincorner.validation.annotations.WalletExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WalletExistValidator implements ConstraintValidator<WalletExists, CashTransferDTO> {

    private final WalletRepository walletRepository;

    @Override
    public boolean isValid(CashTransferDTO value, ConstraintValidatorContext context) {
        Optional<Wallet> walletByIdyId = walletRepository.findById(value.getRecipientWalletId());
        return walletByIdyId.isPresent();
    }
}
