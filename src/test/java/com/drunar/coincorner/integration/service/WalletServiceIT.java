package com.drunar.coincorner.integration.service;

import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.integration.IntegrationTestBase;
import com.drunar.coincorner.service.UserService;
import com.drunar.coincorner.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class WalletServiceIT extends IntegrationTestBase {

    private static final Long WALLET_1 = 1L;
    private static final Long USER_1 = 1L;

    private final WalletService walletService;
    private final UserService userService;

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
        CustomUserDetails user = new CustomUserDetails(1L,"test","123",null);
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
    void delete() {
        assertFalse(walletService.delete(-124L));
        assertTrue(walletService.delete(WALLET_1));
    }


}
