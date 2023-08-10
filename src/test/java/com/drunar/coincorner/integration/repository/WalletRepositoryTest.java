package com.drunar.coincorner.integration.repository;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.database.repository.WalletRepository;
import com.drunar.coincorner.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
public class WalletRepositoryTest extends IntegrationTestBase {

    private final WalletRepository walletRepository;

    @Test
//    @Commit
    void checkAuditing() {
        Wallet wallet = walletRepository.findById(1L).get();
        wallet.setWalletName("WalletCh1");
        walletRepository.flush();
    }

}
