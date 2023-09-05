package com.drunar.coincorner.integration.service;

import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.integration.IntegrationTestBase;
import com.drunar.coincorner.service.CashTransferService;
import com.drunar.coincorner.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class CashTransferServiceIT extends IntegrationTestBase {

    private final WalletService walletService;
    private final CashTransferService cashTransferService;

    private static final Long WALLET_1 = 1L;
    private static final Long WALLET_2 = 3L;

    @Test
    public void cashTransfer() {
        Optional<WalletReadDTO> wallet1 = walletService.findById(WALLET_1);
        assertTrue(wallet1.isPresent());
        BigDecimal senderOldBalance = wallet1.get().getBalance();
        Optional<WalletReadDTO> wallet2 = walletService.findById(WALLET_2);
        assertTrue(wallet2.isPresent());
        BigDecimal recipientOldBalance = wallet2.get().getBalance();

        BigDecimal cashTransfer = BigDecimal.valueOf(100);
        cashTransferService.cashTransfer(WALLET_1,WALLET_2, cashTransfer);

        Optional<WalletReadDTO> senderWallet = walletService.findById(WALLET_1);
        assertTrue(senderWallet.isPresent());
        Optional<WalletReadDTO> recipientWallet = walletService.findById(WALLET_2);
        assertTrue(recipientWallet.isPresent());

        BigDecimal senderNewBalance = senderWallet.get().getBalance();
        BigDecimal recipientNewBalance = recipientWallet.get().getBalance();

        assertNotEquals(senderNewBalance, senderOldBalance);
        assertNotEquals(recipientNewBalance, recipientOldBalance);
        assertEquals(senderNewBalance, senderOldBalance.subtract(cashTransfer));
        assertEquals(recipientNewBalance, recipientOldBalance.add(cashTransfer));

    }



}
