package com.drunar.coincorner.http.rest;

import com.drunar.coincorner.dto.MoneyFormDTO;
import com.drunar.coincorner.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletRestController {

    private final WalletService walletService;

    @PostMapping("/wallets/{walletId}/update-balance")
    public ResponseEntity<?> updateWalletBalance(
            @PathVariable Long walletId,
            @RequestBody MoneyFormDTO moneyForm) {

        walletService.updateBalance(walletId, moneyForm.getAmount());

        return ResponseEntity.ok().build();
    }

}
