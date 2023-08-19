package com.drunar.coincorner.integration.database.repository;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.drunar.coincorner.database.entity.Wallet.Currency;
import static com.drunar.coincorner.database.entity.Wallet.WalletType;
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
    void checkFilterWalletName() {
        WalletFilter filter = WalletFilter.builder().walletName("wallet 1").build();
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(1);
        assertThat(wallets).as("The size of the list should not be 16").isNotEqualTo(16);

    }

    @Test
    void checkFilterWalletType() {
        WalletFilter filter = WalletFilter.builder().walletType(WalletType.CREDIT).build();
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(8);
    }

    @Test
    void checkFilterWalletCurrency() {
        WalletFilter filter = WalletFilter.builder().currency(Currency.UAH).build();
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(2);
    }

    @Test
    void checkFilterByUserId() {

        WalletFilter filter = WalletFilter.builder().ownerUserId(3L).build();
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(3);
    }

    @Test
    void checkFilterAllParameters() {

        WalletFilter filter = WalletFilter.builder()
                .walletName("Wallet 1")
                .walletType(WalletType.DEBIT)
                .currency(Currency.USD)
                .ownerUserId(1L)
                .build();
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(1);
    }

    @Test
//    @Commit
    void checkAuditing() {
        Wallet wallet = walletRepository.findById(1L).get();
        wallet.setWalletName("WalletCh1");
        walletRepository.flush();
    }

}
