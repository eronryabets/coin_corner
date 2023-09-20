package com.drunar.coincorner.integration.service;

import com.drunar.coincorner.database.entity.Role;
import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.integration.IntegrationTestBase;
import com.drunar.coincorner.service.UserServiceImpl;
import com.drunar.coincorner.service.WalletServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class WalletServiceIT extends IntegrationTestBase {

    private static final Long WALLET_1 = 1L;
    private static final Long USER_1 = 1L;

    private final WalletServiceImpl walletService;
    private final UserServiceImpl userService;

    @Test
    void findAll(){
        List<WalletReadDTO> result = walletService.findAll();
        assertThat(result).hasSize(16);
    }

    @Test
    void findById() {
        Optional<WalletReadDTO> maybeWallet = walletService.findById(WALLET_1);
        assertTrue(maybeWallet.isPresent());
        maybeWallet.ifPresent(wallet -> assertEquals("Wallet 1", wallet.getWalletName()));
    }

    @Test
    void findAllByUser(){
        Optional<UserReadDTO> maybeUser = userService.findById(USER_1);
        maybeUser.ifPresent(userReadDTO -> {
            Optional<List<WalletReadDTO>> maybeWallets = walletService.findAllByUser(userReadDTO);
            maybeWallets.ifPresent( wallets -> {
                assertThat(wallets).hasSize(3);
            });
        });
    }

    @Test
    void findAllByUserDetails(){
        User newUser = User.builder()
                .id(1L)
                .username("test")
                .password("123")
                .roles(new HashSet<>(List.of(Role.USER)))
                .build();
        CustomUserDetails user = new CustomUserDetails(newUser);
        Optional<List<WalletReadDTO>> maybeWallets = walletService.findAllByUserDetails(user);
        maybeWallets.ifPresent( wallets -> {
            assertThat(wallets).hasSize(3);
        });
    }

    @Test
    void create(){
        WalletCreateEditDTO walletDTO = new WalletCreateEditDTO(
                "walletName",
                "DEBIT",
                "USD",
                USER_1,
                BigDecimal.valueOf(0));

        WalletReadDTO actualResult = walletService.create(walletDTO);

        assertEquals(walletDTO.getWalletName(), actualResult.getWalletName());
        assertEquals(walletDTO.getWalletType(), actualResult.getWalletType());
        assertEquals(walletDTO.getCurrency(), actualResult.getCurrency());
        assertEquals(walletDTO.getOwnerId(), actualResult.getOwnerId());
        assertEquals(walletDTO.getBalance(), actualResult.getBalance());

    }

    @Test
    void update(){
        WalletCreateEditDTO walletDTO = new WalletCreateEditDTO(
                "walletName2",
                "CREDIT",
                "UAH",
                USER_1,
                BigDecimal.valueOf(100.50));

        Optional<WalletReadDTO> actualResult = walletService.update(WALLET_1,walletDTO);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(wallet -> {
            assertEquals(walletDTO.getWalletName(), wallet.getWalletName());
            assertEquals(walletDTO.getWalletType(), wallet.getWalletType());
            assertEquals(walletDTO.getCurrency(), wallet.getCurrency());
            assertEquals(walletDTO.getOwnerId(), wallet.getOwnerId());
            assertEquals(walletDTO.getBalance(), wallet.getBalance());

        });
    }

    @Test
    void updateBalanceIncome(){
        Optional<WalletReadDTO> wallet = walletService.findById(WALLET_1);
        assertTrue(wallet.isPresent());
        BigDecimal oldBalance = wallet.get().getBalance();
        BigDecimal income = BigDecimal.valueOf(100);
        BigDecimal expectedBalance = oldBalance.add(income);
        walletService.updateBalance(WALLET_1,income);

        Optional<WalletReadDTO> refreshWallet = walletService.findById(WALLET_1);
        assertTrue(refreshWallet.isPresent());
        BigDecimal newBalance = refreshWallet.get().getBalance();

        assertEquals(newBalance, expectedBalance);
        assertNotEquals(newBalance, oldBalance);

    }

    @Test
    void updateBalanceExpense(){
        Optional<WalletReadDTO> wallet = walletService.findById(WALLET_1);
        assertTrue(wallet.isPresent());
        BigDecimal oldBalance = wallet.get().getBalance();
        BigDecimal expense = BigDecimal.valueOf(-100);
        BigDecimal expectedBalance = oldBalance.add(expense);
        walletService.updateBalance(WALLET_1,expense);

        Optional<WalletReadDTO> refreshWallet = walletService.findById(WALLET_1);
        assertTrue(refreshWallet.isPresent());
        BigDecimal newBalance = refreshWallet.get().getBalance();

        assertEquals(newBalance, expectedBalance);
        assertNotEquals(newBalance, oldBalance);

    }

    @Test
    void delete() {
        assertFalse(walletService.delete(-124L));
        assertTrue(walletService.delete(WALLET_1));
    }


}
