package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class WalletTransactionTest {

    @Autowired
    WalletTransactionMapper walletTransactionMapper;

    private Wallet wallet;
    private User user;
    private WalletTransaction walletTransaction;

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

        walletTransaction = WalletTransaction.builder()
                .id(1L)
                .wallet(wallet)
                .previousBalance(BigDecimal.valueOf(1000.0))
                .operationType(WalletTransaction.OperationType.INCOME)
                .transactionDate(LocalDateTime.now())
                .amount(BigDecimal.valueOf(500.50))
                .currentBalance(BigDecimal.valueOf(1500.50))
                .build();
    }

    @Test
    void shouldProperlyMapWalletTransactionalToWalletTransactionalDTO() {
        WalletTransactionDTO dto = walletTransactionMapper.walletTransactionToWalletTransactionDTO(walletTransaction);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(walletTransaction.getId(), dto.getId());
        Assertions.assertEquals(walletTransaction.getWallet().getId(), dto.getWalletId());
        Assertions.assertEquals(walletTransaction.getWallet().getWalletName(), dto.getWalletName());
        Assertions.assertEquals(walletTransaction.getPreviousBalance(), dto.getPreviousBalance());
        Assertions.assertEquals(walletTransaction.getCurrentBalance(), dto.getCurrentBalance());
        Assertions.assertEquals(walletTransaction.getOperationType().name(), dto.getOperationType());
        Assertions.assertEquals(walletTransaction.getTransactionDate(), dto.getTransactionDate());
        Assertions.assertEquals(walletTransaction.getAmount(), dto.getAmount());

    }

    @Test
    void shouldProperlyMapWalletTransactionalDTOToWalletTransactional() {
        WalletTransactionDTO dto = walletTransactionMapper.walletTransactionToWalletTransactionDTO(walletTransaction);
        System.out.println(dto);
        WalletTransaction walletTransaction1 = walletTransactionMapper.walletTransactionDTOToWallet(dto);
        System.out.println(walletTransaction1);

        Assertions.assertNotNull(walletTransaction1);
        Assertions.assertEquals(walletTransaction1.getId(), dto.getId());
        Assertions.assertEquals(walletTransaction1.getWallet().getId(), dto.getWalletId());
        Assertions.assertEquals(walletTransaction1.getWallet().getWalletName(), dto.getWalletName());
        Assertions.assertEquals(walletTransaction1.getPreviousBalance(), dto.getPreviousBalance());
        Assertions.assertEquals(walletTransaction1.getCurrentBalance(), dto.getCurrentBalance());
        Assertions.assertEquals(walletTransaction1.getOperationType().name(), dto.getOperationType());
        Assertions.assertEquals(walletTransaction1.getTransactionDate(), dto.getTransactionDate());
        Assertions.assertEquals(walletTransaction1.getAmount(), dto.getAmount());

    }


}
