package com.drunar.coincorner.integration.service;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.dto.WalletReadDTO;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import com.drunar.coincorner.integration.IntegrationTestBase;
import com.drunar.coincorner.mapper.WalletMapper;
import com.drunar.coincorner.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RequiredArgsConstructor
public class WalletTransactionServiceIT extends IntegrationTestBase {

    private final WalletTransactionService walletTransactionService;
    private final WalletMapper walletMapper;

    private Wallet wallet;
    private User user;
    private WalletReadDTO walletDTO;

    @BeforeEach
    void init(){
        user = User.builder()
                .id(1L)
                .email("user1@example.com")
                .username("user1")
                .firstname("John")
                .lastname("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();

        wallet = Wallet.builder()
                .id(1L)
                .walletName("Wallet 1")
                .currency(Wallet.Currency.USD)
                .balance(BigDecimal.valueOf(1000.00))
                .ownerUser(user)
                .walletType(Wallet.WalletType.DEBIT)
                .build();

        walletDTO = walletMapper.walletToWalletReadDTO(wallet);

    }

    @Test
    void findAll(){
        List<WalletTransactionDTO> result = walletTransactionService.findAll();
        assertThat(result).hasSize(15);
    }

    @Test
    void findAllByWallet(){
        Optional<List<WalletTransactionDTO>> transactionDTO = walletTransactionService.findAllByWallet(walletDTO);
        transactionDTO.ifPresent(it -> {
            assertEquals(it.size(),6);
            assertEquals(it.get(0).getWalletId(), wallet.getId());
            assertEquals(it.get(0).getWalletName(), wallet.getWalletName());
        });

    }

    @Test
    void create(){
        WalletTransactionDTO transaction1 = new WalletTransactionDTO(1L,
                1L,
                "Wallet 1",
                BigDecimal.valueOf(1000.00),
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(7777.77),
                "INCOME",
                LocalDateTime.of(2023, 8, 15, 14, 30)

        );

        WalletTransactionDTO actualResult = walletTransactionService.create(transaction1);
        System.out.println(actualResult);

        assertEquals(transaction1.getId(), actualResult.getId());
        assertEquals(transaction1.getWalletId(), actualResult.getWalletId());
        assertEquals(transaction1.getPreviousBalance(), actualResult.getPreviousBalance());
        assertEquals(transaction1.getAmount(), actualResult.getAmount());
        assertNotEquals(transaction1.getCurrentBalance(), actualResult.getCurrentBalance());
        assertEquals(BigDecimal.valueOf(1100.00), actualResult.getCurrentBalance());
        assertEquals(transaction1.getOperationType(), actualResult.getOperationType());
        assertEquals(transaction1.getTransactionDate(), actualResult.getTransactionDate());


    }

}
