package com.drunar.coincorner.integration.database.repository;

import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.database.filter.WalletTransactionFilter;
import com.drunar.coincorner.database.repository.WalletTransactionRepository;
import com.drunar.coincorner.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.drunar.coincorner.database.entity.WalletTransaction.OperationType;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class WalletTransactionRepositoryTest extends IntegrationTestBase {

    private final WalletTransactionRepository walletTransactionRepository;

    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        Page<WalletTransaction> page = walletTransactionRepository.findAllBy(pageable);
        page.forEach(walletTr -> System.out.println(walletTr.getId()));

        while (page.hasNext()) {
            page = walletTransactionRepository.findAllBy(page.nextPageable());
            page.forEach(walletTr -> System.out.println(walletTr.getId()));
        }
        assertThat(page).hasSize(1);
    }

    @Test
    void checkWalletTrFilterOperationType() {
        WalletTransactionFilter filter = WalletTransactionFilter.builder().operationType(OperationType.INCOME).build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(8);
        assertThat(transactions).as("The size of the list should not be 5").isNotEqualTo(5);

    }

    @Test
    void checkWalletTrFilterAmount() {
        WalletTransactionFilter filter = WalletTransactionFilter.builder()
                .amount(BigDecimal.valueOf(300))
                .build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(3);

    }

    @Test
    void checkWalletTrFilterTransactionDate() {
        LocalDateTime ldt = LocalDateTime.of(2021,7,15,19,30, 0);

        WalletTransactionFilter filterIn = WalletTransactionFilter.builder().transactionDateIn(ldt).build();
        List<WalletTransaction> transactionsIn = walletTransactionRepository.findAllByFilter(filterIn);

        WalletTransactionFilter filterAfter = WalletTransactionFilter.builder().transactionDateAfter(ldt).build();
        List<WalletTransaction> transactionsAfter = walletTransactionRepository.findAllByFilter(filterAfter);

        WalletTransactionFilter filterBefore = WalletTransactionFilter.builder().transactionDateBefore(ldt).build();
        List<WalletTransaction> transactionsBefore = walletTransactionRepository.findAllByFilter(filterBefore);


        assertThat(transactionsIn).hasSize(1);
        assertThat(transactionsAfter).hasSize(9);
        assertThat(transactionsBefore).hasSize(5);

    }

    @Test
    void checkWalletTrFilterFromDateToDate() {
        WalletTransactionFilter filter = WalletTransactionFilter.builder()
                .transactionDateStart(LocalDateTime.of(2020,1,1,0,0, 0))
                .transactionDateEnd(LocalDateTime.of(2021,1,1,1,0, 0))
                .build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(4);
    }


    @Test
    void checkWalletTrFilterByUserId() {
        WalletTransactionFilter filter = WalletTransactionFilter.builder()
                .userId(1L)
                .build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(13);
    }

    @Test
    void checkWalletTrFilterByUserName() {
        WalletTransactionFilter filter = WalletTransactionFilter.builder()
                .username("user1")
                .build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(13);
    }

    @Test
    void checkWalletTrFilterByWalletId() {

        WalletTransactionFilter filter = WalletTransactionFilter.builder()
                .walletId(1L)
                .walletName("")
                .build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(6);

    }

    @Test
    void checkWalletTrFilterByWalletName() {

        WalletTransactionFilter filter = WalletTransactionFilter.builder()
                .walletName("Wallet 1")
                .build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(6);
        System.out.println(transactions.size());

    }


}
