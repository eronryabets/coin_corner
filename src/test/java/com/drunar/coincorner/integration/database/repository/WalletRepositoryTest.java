package com.drunar.coincorner.integration.database.repository;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.filter.WalletFilter;
import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class WalletRepositoryTest extends IntegrationTestBase {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
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
        WalletFilter filter = new WalletFilter("wall",
                null, null, null);
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(16);
        assertThat(wallets).as("The size of the list should not be 1").isNotEqualTo(1);

    }

    @Test
    void checkFilterWalletType() {
        WalletFilter filter = new WalletFilter(null,
                Wallet.WalletType.CREDIT, null, null);
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(8);
    }

    @Test
    void checkFilterWalletCurrency() {
        WalletFilter filter = new WalletFilter(null,
                null, Wallet.Currency.UAH, null);
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(2);
    }

    @Test
    void checkFilterByUser() {

        User mockUser = new User();
        mockUser.setId(3L);
        when(userRepository.findById(3L)).thenReturn(Optional.of(mockUser));

        WalletFilter filter = new WalletFilter(null,
                null, null, mockUser);
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(3);
    }

    @Test
    void checkFilterAllParameters() {

        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        WalletFilter filter = new WalletFilter("Wallet 1",
                Wallet.WalletType.DEBIT, Wallet.Currency.USD, mockUser);
        List<Wallet> wallets = walletRepository.findAllByFilter(filter);
        assertThat(wallets).hasSize(1);
    }

}
