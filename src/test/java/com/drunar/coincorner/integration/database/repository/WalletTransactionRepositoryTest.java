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

import java.util.List;

import static com.drunar.coincorner.database.entity.WalletTransaction.*;
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
    void checkCustomImplementation() {
        WalletTransactionFilter filter = WalletTransactionFilter.builder().operationType(OperationType.INCOME).build();
        List<WalletTransaction> transactions = walletTransactionRepository.findAllByFilter(filter);
        assertThat(transactions).hasSize(8);
        assertThat(transactions).as("The size of the list should not be 5").isNotEqualTo(5);

    }

}
