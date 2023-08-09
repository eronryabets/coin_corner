package com.drunar.coincorner.integration.database.repository;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class WalletRepositoryTest extends IntegrationTestBase {

    private final WalletRepository walletRepository;

    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        Page<Wallet> page = walletRepository.findAllBy(pageable);
        page.forEach(wallet -> System.out.println(wallet.getId()));

        while (page.hasNext()) {
            page = walletRepository.findAllBy(page.nextPageable());
            page.forEach(wallet -> System.out.println(wallet.getId()));
        }
        assertThat(page).hasSize(2);
    }

    @Test
    void checkCustomImplementation() {
        WalletFilter filter = new WalletFilter(null,
                Wallet.WalletType.CREDIT, Wallet.Currency.UAH, null);
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(2);
        assertThat(wallets).as("The size of the list should not be 5").isNotEqualTo(5);

    }

}
