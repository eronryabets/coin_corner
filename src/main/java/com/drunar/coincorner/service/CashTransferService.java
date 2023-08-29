package com.drunar.coincorner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CashTransferService {

    private final WalletService walletService;


    @Transactional
    public boolean cashTransfer(Long senderWalletId, Long recipientWalletId, BigDecimal amount){
        return walletService.updateBalance(senderWalletId, amount.negate()) &&
                walletService.updateBalance(recipientWalletId, amount);
    }
}
