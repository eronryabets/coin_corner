package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
import com.drunar.coincorner.dto.WalletReadDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class WalletMapperTest {

    @Autowired
    WalletMapper walletMapper;
    private Wallet wallet;
    private User user;

    @BeforeEach
    void init(){
        user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .birthDate(LocalDate.of(2000, 12, 12))
                .build();

        wallet = Wallet.builder()
                .id(1L)
                .walletName("walletName")
                .currency(Wallet.Currency.UAH)
                .balance(BigDecimal.valueOf(1000.50))
                .ownerUser(user)
                .walletType(Wallet.WalletType.CREDIT)
                .build();
    }

    @Test
    void shouldProperlyMapWalletToWalletReadDTO() {
        WalletReadDTO dto = walletMapper.walletToWalletReadDTO(wallet);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(wallet.getId(), dto.getId());
        Assertions.assertEquals(wallet.getWalletName(), dto.getWalletName());
        Assertions.assertEquals(wallet.getCurrency().name(), dto.getCurrency());
        Assertions.assertEquals(wallet.getBalance(), dto.getBalance());
        Assertions.assertEquals(wallet.getOwnerUser().getId(), dto.getOwnerId());
        Assertions.assertEquals(wallet.getOwnerUser().getUsername(), dto.getOwnerUsername());
        Assertions.assertEquals(wallet.getWalletType().name(), dto.getWalletType());


    }

    @Test
    void shouldProperlyMapWalletReadDTOToWallet() {
        WalletReadDTO dto = walletMapper.walletToWalletReadDTO(wallet);
        Wallet wallet1 = walletMapper.walletReadDTOToWallet(dto);

        Assertions.assertNotNull(wallet1);
        Assertions.assertEquals(wallet1.getId(), dto.getId());
        Assertions.assertEquals(wallet1.getWalletName(), dto.getWalletName());
        Assertions.assertEquals(wallet1.getCurrency().name(), dto.getCurrency());
        Assertions.assertEquals(wallet1.getBalance(), dto.getBalance());
        Assertions.assertEquals(wallet1.getOwnerUser().getId(), dto.getOwnerId());
        Assertions.assertEquals(wallet1.getWalletType().name(), dto.getWalletType());

    }

    @Test
    void shouldProperlyMapWalletToWalletCreateEditDTO() {
        WalletCreateEditDTO dto = walletMapper.walletToWalletCreateEditDTO(wallet);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(wallet.getWalletName(), dto.getWalletName());
        Assertions.assertEquals(wallet.getCurrency().name(), dto.getCurrency());
        Assertions.assertEquals(wallet.getBalance(), dto.getBalance());
        Assertions.assertEquals(wallet.getOwnerUser().getId(), dto.getOwnerId());
        Assertions.assertEquals(wallet.getWalletType().name(), dto.getWalletType());

    }

    @Test
    void shouldProperlyMapWalletCreateEditDTOToWallet() {
        WalletCreateEditDTO dto = walletMapper.walletToWalletCreateEditDTO(wallet);
        Wallet wallet1 = walletMapper.walletCreateEditDTOToWallet(dto);

        Assertions.assertNotNull(wallet1);
        Assertions.assertEquals(wallet1.getWalletName(), dto.getWalletName());
        Assertions.assertEquals(wallet1.getCurrency().name(), dto.getCurrency());
        Assertions.assertEquals(wallet1.getBalance(), dto.getBalance());
        Assertions.assertEquals(wallet1.getOwnerUser().getId(), dto.getOwnerId());
        Assertions.assertEquals(wallet1.getWalletType().name(), dto.getWalletType());

    }



}
